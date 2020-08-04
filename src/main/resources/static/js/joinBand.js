const divSkills = document.getElementById("join");
const btnAdd = document.getElementById("join-band");
let divForm1 = document.getElementById("my-form1");
let divForm2 = document.getElementById("my-form2");
let btnCancel = document.getElementById("btn-cancel");

if (btnAdd) {
    btnAdd.addEventListener('click', show);
}
if (btnCancel) {
    btnCancel.addEventListener('click', hide);
}


function show() {
        divForm1.classList.add("hidden");
        divForm2.classList.add("hidden");
        divSkills.classList.remove("hidden");
}

function hide() {
    divSkills.classList.add("hidden");
    divForm1.classList.remove("hidden");
    divForm2.classList.remove("hidden");
}