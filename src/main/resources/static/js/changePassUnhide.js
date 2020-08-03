const divChangePass = document.getElementById("change-password");
const btnChangePass = document.getElementById("btnChangePass");
let trackChange = true;
btnChangePass.addEventListener('click', showChangePass);
function showChangePass() {
    if(trackChange) {
        divChangePass.classList.remove("hidden");
        trackChange = false;
        btnChangePass.textContent = "Hide";
        divChangePass.scrollIntoView(true);
    } else {
        divChangePass.classList.add("hidden");
        trackChange = true;
        btnChangePass.textContent = "Change password";
    }
}