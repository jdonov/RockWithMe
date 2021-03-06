window.addEventListener('load', () => {
    const localHost = "http://localhost:8080";
    const instrumentInput = document.getElementById("instrument");
    const levelInput = document.getElementById("level");

    const divContainer = document.getElementById("search-container");
    const divList = document.getElementById("pagination");
    document.getElementById("btnSearchPlayer").addEventListener('click', searchUser);
    let sObj = {};
    let size = 2;
    let currentPage = 1;

    async function searchUser(e) {
        e.preventDefault();
        searchObj();
        while (divList.firstChild) {
            divList.removeChild(divList.firstChild);
        }
        let pages = await loadData(1, size);
        if (pages > 0) {
            await renderList(pages);
            liStyle();
        }
    }

    async function loadData(page, size) {
        let response = await searchSend(sObj, page, size);
        while (divContainer.firstChild) {
            divContainer.removeChild(divContainer.firstChild);
        }
        if (response.content.length !== 0) {
            response.content.forEach(b => {
                divContainer.appendChild(renderUser(b));
            });
            return Number(response.totalPages);
        } else {
            let h = document.createElement("h3");
            h.setAttribute("id", "no-bands-message")
            h.textContent = "No players found!";
            divContainer.appendChild(h);
            return 0;
        }
    }

    function searchObj() {
        const search = {
            instrument: instrumentInput.value,
            level: levelInput.value
        }
        sObj = Object.fromEntries(Object.entries(search).filter(([key, value]) => value));
    }

    function renderList(totalPages) {
        let ulList = document.createElement("ul");
        ulList.classList.add("pagination");
        createLi(ulList, currentPage, totalPages, false, true, `\u00ab`);
        for (let i = 1; i <= totalPages; i++) {
            createLi(ulList, i, totalPages, true);
        }
        createLi(ulList, currentPage, totalPages, false, false, `\u00bb`);
        divList.appendChild(ulList);
    }

    function createLi(ul, numb, totalPages, other, firstLast, str) {
        let li = document.createElement("li");
        li.classList.add("page-item");
        li.classList.add("my-search-li");
        let a = document.createElement("a");
        if (!other) {
            a.textContent = str;
        } else {
            a.textContent = numb;
        }
        a.classList.add("page-link");
        a.addEventListener("click", searchBandsPages);
        li.appendChild(a);
        ul.appendChild(li);

        async function searchBandsPages(e) {
            e.preventDefault();
            if (other) {
                currentPage = numb;
                liStyle();
                await loadData(numb, size);
            } else {
                if (firstLast){
                    if(currentPage > 1){
                        currentPage--;
                        liStyle();
                        await loadData(currentPage, size);
                    }
                } else {
                    if(currentPage < totalPages){
                        currentPage++;
                        liStyle();
                        await loadData(currentPage, size);
                    }
                }
            }
        }
    }

    function liStyle(){
        let allLi = Array.from(document.getElementsByClassName("my-search-li"));
        for (let i = 0; i < allLi.length; i++){
            allLi[i].classList.remove("disabled", "active");
        }
        for (let i = 0; i < allLi.length; i++) {
            if (i === 0) {
                if(currentPage <= 1) {
                    allLi[i].classList.add("disabled");
                    allLi[i + 1].classList.add("active");
                }
            } else if (i === (allLi.length - 2)){
                if(currentPage >= (allLi.length - 2)){
                    allLi[i].classList.add("active");
                    allLi[i + 1].classList.add("disabled");
                }
            } else {
                if(i === currentPage){
                    allLi[i].classList.add("active");
                }
            }
        }
    }


    async function searchSend(searchObj, page, size) {
        const response = await fetch(`${localHost}/api/skills/search?page=${page}&size=${size}`, {
            method: "POST",
            body: JSON.stringify(searchObj),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return await response.json();
    }

    function renderUser(user) {
        let divUser = document.createElement("div");
        divUser.classList.add("my-bcg", "border-bottom","border-light", "mt-2", "mb-2");
        let linkUser = document.createElement("a");
        linkUser.setAttribute("href", `${localHost}/users/details/${user.userId}`);
        let spanName = document.createElement("h6");
        spanName.textContent = `Username: ${user.username}`;
        linkUser.appendChild(spanName);
        divUser.appendChild(linkUser);

        let pInfo = document.createElement("p");
        pInfo.classList.add("my-search-p");
        pInfo.textContent = "Instrument: ";
        let instrument = user.instrument.replace("_", " ");
        instrument = instrument.toLowerCase();
        instrument = instrument.charAt(0).toUpperCase() + instrument.slice(1);
        pInfo.textContent += instrument;
        pInfo.textContent += ", Level: ";
        pInfo.textContent += user.level;
        linkUser.appendChild(pInfo);
        return divUser;
    }
})