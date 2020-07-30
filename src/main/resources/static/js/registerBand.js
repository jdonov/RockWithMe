const nameInput = document.getElementById("name");
const hasStudioInput = document.getElementsByName("hasStudio");
const needsProducerInput = document.getElementsByName("needsProducer");
const townInput = document.getElementById("town");
const founderInstrument = document.getElementById("founder-instrument");
const descriptionInput = document.getElementById('description');
const instrumentsEl = document.getElementById("instrumentsContainer");
const stylesEl = document.getElementById("stylesContainer");
const goalsEl = document.getElementById("goalsContainer");
document.getElementById("btnRegister").addEventListener("click", registerBand);
document.getElementById("instruments").addEventListener("change", chooseInstr);
document.getElementById("style").addEventListener("change", chooseStyle);
document.getElementById("goal").addEventListener("change", chooseGoal);
let instrArr = [];
let stylesArr = [];
let goalsArr = [];

function createEl(e, targetEl, targetArr) {
    let nameEl = e.target.value;
    let divEl = document.createElement("div");
    let spanEl = document.createElement("span");
    let spanBtn = document.createElement("span");
    let btn = document.createElement("button");
    btn.textContent = "X";
    btn.addEventListener("click", delEl);
    spanEl.textContent = nameEl;
    spanBtn.appendChild(btn);
    divEl.appendChild(spanEl);
    divEl.appendChild(spanBtn);
    targetEl.appendChild(divEl);
    targetArr.push(nameEl);

    function delEl() {
        let index = targetArr.indexOf(nameEl);
        targetArr.splice(index, 1);
        divEl.remove();
    }
}

function chooseInstr(e) {
    createEl(e, instrumentsEl, instrArr);
}

function chooseStyle(e) {
    if (stylesArr.indexOf(e.target.value) >= 0) {
        return;
    }
    createEl(e, stylesEl, stylesArr);
}

function chooseGoal(e) {
    if (goalsArr.indexOf(e.target.value) >= 0) {
        return;
    }
    createEl(e, goalsEl, goalsArr);
}

async function registerBand(e) {
    e.preventDefault();
    let studio = Array.from(hasStudioInput).find(el => el.checked).value;
    let producer = Array.from(needsProducerInput).find(el => el.checked).value;
    const band = {
        name: nameInput.value,
        instruments: instrArr,
        styles: stylesArr,
        hasStudio: studio,
        needsProducer: producer,
        goals: goalsArr,
        town: townInput.value,
        founderInstrument: founderInstrument.value,
        description: descriptionInput.value
    };
    try {
        await createBand(band);
    } catch (e) {
        console.log(e);
    }

}

async function createBand(band) {
    const response = await fetch("http://localhost:8080/bands/register", {
        method: "POST",
        body: JSON.stringify(band),
        headers: {
            'Content-type': "application/json"
        }
    });
    return await response.json();
    // return await response.text();
    // document.body.innerHTML = html;
}
