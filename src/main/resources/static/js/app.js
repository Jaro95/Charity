document.addEventListener("DOMContentLoaded", function() {

  /**
   * Form Select
   */
  class FormSelect {
    constructor($el) {
      this.$el = $el;
      this.options = [...$el.children];
      this.init();
    }

    init() {
      this.createElements();
      this.addEvents();
      this.$el.parentElement.removeChild(this.$el);
    }

    createElements() {
      // Input for value
      this.valueInput = document.createElement("input");
      this.valueInput.type = "text";
      this.valueInput.name = this.$el.name;

      // Dropdown container
      this.dropdown = document.createElement("div");
      this.dropdown.classList.add("dropdown");

      // List container
      this.ul = document.createElement("ul");

      // All list options
      this.options.forEach((el, i) => {
        const li = document.createElement("li");
        li.dataset.value = el.value;
        li.innerText = el.innerText;

        if (i === 0) {
          // First clickable option
          this.current = document.createElement("div");
          this.current.innerText = el.innerText;
          this.dropdown.appendChild(this.current);
          this.valueInput.value = el.value;
          li.classList.add("selected");
        }

        this.ul.appendChild(li);
      });

      this.dropdown.appendChild(this.ul);
      this.dropdown.appendChild(this.valueInput);
      this.$el.parentElement.appendChild(this.dropdown);
    }

    addEvents() {
      this.dropdown.addEventListener("click", e => {
        const target = e.target;
        this.dropdown.classList.toggle("selecting");

        // Save new value only when clicked on li
        if (target.tagName === "LI") {
          this.valueInput.value = target.dataset.value;
          this.current.innerText = target.innerText;
        }
      });
    }
  }
  document.querySelectorAll(".form-group--dropdown select").forEach(el => {
    new FormSelect(el);
  });

  /**
   * Hide elements when clicked on document
   */
  document.addEventListener("click", function(e) {
    const target = e.target;
    const tagName = target.tagName;

    if (target.classList.contains("dropdown")) return false;

    if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
      return false;
    }

    if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
      return false;
    }

    document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
      el.classList.remove("selecting");
    });
  });

  /**
   * Switching between form steps
   */
  class FormSteps {
    constructor(form) {
      this.$form = form;
      this.$next = form.querySelectorAll(".next-step");
      this.$prev = form.querySelectorAll(".prev-step");
      this.$step = form.querySelector(".form--steps-counter span");
      this.currentStep = 1;

      this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
      const $stepForms = form.querySelectorAll("form > div");
      this.slides = [...this.$stepInstructions, ...$stepForms];

      this.init();
    }

    /**
     * Init all methods
     */
    init() {
      this.events();
      this.updateForm();
    }

    /**
     * All events that are happening in form
     */
    events() {
      // Next step
      this.$next.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep++;
          this.updateForm();
        });
      });

      // Previous step
      this.$prev.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep--;
          this.updateForm();
        });
      });

      // Form submit
      this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
    }

    /**
     * Update form front-end
     * Show next or previous section etc.
     */
    updateForm() {
      this.$step.innerText = this.currentStep;

      // TODO: Validation

      this.slides.forEach(slide => {
        slide.classList.remove("active");

        if (slide.dataset.step == this.currentStep) {
          slide.classList.add("active");
        }
      });

      this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 5;
      this.$step.parentElement.hidden = this.currentStep >= 5;

      // TODO: get data from inputs and show them in summary
    }

  }
  const form = document.querySelector(".form--steps");
  if (form !== null) {
    new FormSteps(form);
  }

  const lastStep = document.querySelector("#last-step")
  lastStep.addEventListener("click",function (e){
    let checkedBoxValues = [];
    const quantityBag = document.getElementById("quantityBag")
    let checkboxes = document.querySelectorAll("#categoriesChoose input[type='checkbox']:checked")
    const quantityBagSend = document.getElementById("quantityBagSend")
    checkboxes.forEach((checkbox) => {
      checkedBoxValues.push(checkbox.getAttribute('data-category-name'));
    });
    quantityBagSend.innerText = "W workach znajdą się:\n" +  checkedBoxValues.join('\n')
        + "\nIlość worków: " + quantityBag.value;
    const institution = document.querySelector("#institutionChoose input[type='radio']:checked")
    const institutionSelect = document.getElementById("institutionSelect")
    institutionSelect.innerText = 'Dla fundacji "' + institution.getAttribute('data-institution-name') +'" ';
    const street = document.getElementById("street");
    const inputStreet = document.getElementById("inputStreet")
    inputStreet.innerText = street.value === "" ? "Brak ulicy" : street.value
    const city = document.getElementById("city");
    const inputCity = document.getElementById("inputCity")
    inputCity.innerText = city.value === "" ? "Brak miasta" : city.value
    const zipCode = document.getElementById("zipCode");
    const inputZipCode = document.getElementById("inputZipCode")
    inputZipCode.innerText = zipCode.value === "" ? "Brak kodu pocztowego" : zipCode.value
    const phoneNumber = document.getElementById("phoneNumber");
    const inputPhoneNumber = document.getElementById("inputPhoneNumber")
    inputPhoneNumber.innerText = phoneNumber.value === "0" ? "Brak nr kontaktowego" : phoneNumber.value
    const pickUpDate = document.getElementById("pickUpDate");
    const inputPickUpDate = document.getElementById("inputPickUpDate")
    inputPickUpDate.innerText = pickUpDate.value === "" ? "Nie podano daty odbioru" : pickUpDate.value
    const pickUpTime = document.getElementById("pickUpTime");
    const inputPickUpTime = document.getElementById("inputPickUpTime")
    inputPickUpTime.innerText = pickUpTime.value === "" ? "Nie podano czasu odbioru" : pickUpTime.value
    const pickUpComment = document.getElementById("pickUpComment");
    const inputPickUpComment = document.getElementById("inputPickUpComment")
    inputPickUpComment.innerText = pickUpComment.value === "" ? "Brak uwag" : pickUpComment.value
  })
});
$(document).ready(function () {
  var table = $('#donationUserTable').DataTable({
    "paging": true,
    "ordering": true,
    "info": true
  });

  $('#searchBox').on('keyup', function () {
    table.search(this.value).draw();
  });
});
$('form').on('submit', function(e) {
  e.preventDefault(); // to zapobiegnie domyślnej akcji
});

$(document).ready(function () {
  var table = $('#updateDonationUser').DataTable({
    "paging": false,
    "ordering": false,
    "info": false,
    "dom": 't'
  });

  $('#searchBox').on('keyup', function () {
    table.search(this.value).draw();
  });
});

document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('#delete-donation').forEach(link => {
    link.addEventListener('click', event => {
      if (!confirm('Czy na pewno chcesz usunąć dar?')) {
        event.preventDefault();
      }
    });
  });
});
