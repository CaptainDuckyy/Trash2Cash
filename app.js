const STORAGE_KEY = "trash2cash.items";

function escapeHtml(value) {
  return String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

function preparePhoto(file) {
  if (!file) return Promise.resolve("");

  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      const image = new Image();
      image.onload = () => {
        const maxSize = 1200;
        const scale = Math.min(1, maxSize / Math.max(image.naturalWidth, image.naturalHeight));
        const canvas = document.createElement("canvas");
        canvas.width = Math.max(1, Math.round(image.naturalWidth * scale));
        canvas.height = Math.max(1, Math.round(image.naturalHeight * scale));
        canvas.getContext("2d").drawImage(image, 0, 0, canvas.width, canvas.height);

        try {
          resolve(canvas.toDataURL("image/jpeg", 0.82));
        } catch (error) {
          reject(error);
        }
      };
      image.onerror = () => reject(new Error("The selected image could not be read."));
      image.src = reader.result;
    };
    reader.onerror = () => reject(reader.error);
    reader.readAsDataURL(file);
  });
}

function readWasteForm() {
  const form = document.querySelector("#waste-form");
  if (!form) return;

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const items = [];

    for (let rowNumber = 1; rowNumber <= 5; rowNumber += 1) {
      const description = form.elements[`description${rowNumber}`].value.trim();
      const file = form.elements[`photo${rowNumber}`].files[0];

      if (!description && !file) continue;

      let photoData = "";
      try {
        photoData = await preparePhoto(file);
      } catch (error) {
        alert(error.message);
        return;
      }

      items.push({
        rowNumber,
        photoData,
        mimeType: file?.type || "",
        description,
        condition: form.elements[`condition${rowNumber}`].value,
        userMarkedRecyclable: form.elements[`recyclable${rowNumber}`].value === "yes",
        weightKg: Number(form.elements[`weight${rowNumber}`].value) || 0,
        quantity: Number(form.elements[`quantity${rowNumber}`].value) || 1
      });
    }

    if (items.length === 0) {
      alert("Add a photo or description for at least one item.");
      return;
    }

    sessionStorage.setItem(STORAGE_KEY, JSON.stringify(items));
    window.location.href = "result.html";
  });
}

function renderWasteResults() {
  const layout = document.querySelector(".results-layout");
  if (!layout) return;

  const items = JSON.parse(sessionStorage.getItem(STORAGE_KEY) || "[]");
  if (items.length === 0) {
    document.querySelector(".empty-box")?.classList.remove("hide");
    layout.classList.add("hide");
    return;
  }

  const radios = items.map((item, index) =>
    `<input type="radio" name="item" id="choose${index + 1}"${index === 0 ? " checked" : ""}>`
  ).join("");

  const tabs = items.map((item, index) => {
    const name = item.description || `Item ${item.rowNumber}`;
    return `
      <label class="item-tab" for="choose${index + 1}">
        <span class="thumb">${item.photoData ? `<img src="${item.photoData}" alt="">` : ""}</span>
        <span class="item-tab-text">
          <span class="item-tab-name">${escapeHtml(name)}</span>
          <span class="item-tab-type">${item.userMarkedRecyclable ? "Marked recyclable" : "Marked not recyclable"}</span>
        </span>
      </label>`;
  }).join("");

  const details = items.map((item, index) => {
    const name = item.description || `Item ${item.rowNumber}`;
    const photo = item.photoData
      ? `<img src="${item.photoData}" alt="${escapeHtml(name)}">`
      : `<span class="photo-label">No photo uploaded</span>`;

    return `
      <article class="item-detail" id="detail${index + 1}">
        <div class="photo-box">
          ${photo}
          <div class="photo-caption">
            <strong>${escapeHtml(name)}</strong>
            <span class="badge">${item.condition.toLowerCase()}</span>
            <span class="badge">${item.quantity} item${item.quantity === 1 ? "" : "s"}</span>
          </div>
        </div>
        <div class="column">
          ${item.userMarkedRecyclable
            ? `<div class="action"><h3>Reuse or recycle</h3><p>Keep reusable parts and follow local recycling rules for this item.</p></div>`
            : `<div class="warning"><span class="warning-icon">!</span><p>Double-check local rules before disposal.</p></div>`}
          <p class="small-text">Weight: ${item.weightKg} kg | Quantity: ${item.quantity}</p>
          <form class="calc">
            <h3>Profit calculator</h3>
            <div class="calc-fields">
              <label>Cost to make one<input type="number" name="cost" min="0" step="0.01"></label>
              <label>How many<input type="number" name="made" min="1" value="${item.quantity}"></label>
              <label>Your price<input type="number" name="price" min="0" step="0.01"></label>
            </div>
            <div class="calc-bottom">
              <button type="submit" class="button small">Calculate</button>
              <p class="calc-result">Enter the values to calculate possible profit.</p>
            </div>
          </form>
        </div>
        <div class="column">
          <h3>Upcycling ideas</h3>
          <p>Use the item description and condition to plan a reuse or repair project.</p>
        </div>
      </article>`;
  }).join("");

  layout.innerHTML = `
    ${radios}
    <aside class="white-box item-list">
      <h2>Your items</h2>
      ${tabs}
      <a class="button" href="upload.html">Check more items</a>
    </aside>
    <section class="white-box details-box">${details}</section>`;

  layout.querySelectorAll(".calc").forEach((form) => {
    form.addEventListener("submit", (event) => {
      event.preventDefault();
      const cost = Number(form.elements.cost.value) || 0;
      const made = Number(form.elements.made.value) || 0;
      const price = Number(form.elements.price.value) || 0;
      const profit = (price - cost) * made;
      form.querySelector(".calc-result").textContent =
        `Possible profit: ${profit.toFixed(2)}.`;
    });
  });
}

readWasteForm();
renderWasteResults();
