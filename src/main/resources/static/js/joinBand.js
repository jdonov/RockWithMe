const divSkills = document.getElementById("join");
const btnAdd = document.getElementById("join-band");
let bandImg = document.getElementById("my-img");
let btnCancel = document.getElementById("btn-cancel");
if (btnAdd) {
    btnAdd.addEventListener('click', show);
}
if (btnCancel) {
    btnCancel.addEventListener('click', hide);
}


function show() {
        bandImg.classList.add("hidden");
        divSkills.classList.remove("hidden");
        trackChange = false;
        btnAdd.classList.add("hidden");

}

function hide() {
    divSkills.classList.add("hidden");
    bandImg.classList.remove("hidden");
    btnAdd.classList.remove("hidden");
}