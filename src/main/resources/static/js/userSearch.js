window.addEventListener('load', () => {
    const localHost = "http://localhost:8080";
    const instrumentInput = document.getElementById("instrument");
    const levelInput = document.getElementById("level");

    const divContainer = document.getElementById("search-container");
    const divList = document.getElementById("pagination");
    document.getElementById("btnSearchPlayer").addEventListener('click', searchUser);
    let sObj = {};
    let size = 2;

    async function searchUser(e) {
        e.preventDefault();
        searchObj();
        while (divList.firstChild) {
            divList.removeChild(divList.firstChild);
        }
        let pages = await loadData(1, size);
        if (pages > 0) {
            await renderList(pages);
        }
    }

    async function loadData(page, size) {
        let response = await searchSend(sObj, page, size);
        while (divContainer.firstChild) {
            divContainer.removeChild(divContainer.firstChild);
        }
        console.log(response);
        if (response.content.length !== 0) {
            response.content.forEach(b => {
                divContainer.appendChild(renderUser(b));
            });
            return Number(response.totalPages);
        } else {
            let h = document.createElement("h3");
            h.setAttribute("id", "no-bands-message")
            h.textContent = "No bands found!";
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
        for (let i = 1; i <= totalPages; i++) {
            let li = document.createElement("li");
            let btn = document.createElement("button");
            btn.textContent = i;
            btn.addEventListener("click", searchBandsPages);
            li.appendChild(btn);
            ulList.appendChild(li);

            async function searchBandsPages(e) {
                e.preventDefault();
                await loadData(i, size);
            }
        }
        divList.appendChild(ulList);
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
        let linkUser = document.createElement("a");
        linkUser.setAttribute("href", `${localHost}/users/details/${user.id}`);
        let spanName = document.createElement("span");
        spanName.textContent = `Username: ${user.username}`;
        linkUser.appendChild(spanName);
        divUser.appendChild(linkUser);

        let pInstr = document.createElement("p");
        pInstr.textContent = user.instrument;
        linkUser.appendChild(pInstr);

        let pLevel = document.createElement("p");
        pLevel.textContent = user.level;
        linkUser.appendChild(pLevel);

        return divUser;
    }
})