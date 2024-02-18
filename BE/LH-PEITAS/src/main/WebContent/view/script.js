const arrowDownIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAJxJREFUSEvtk9ENgCAMRI9NHE0nUSfR0RxFm0jSIO1VE34MfB/36AMSGq/UuB8dQA3/X9EA4KAenICnaLvLVwJwcxZANo138QLAgtCcBZivcinOqwbR5ZLbAUzltJ4iDxIqFxh7pjWIXHzWZ548T8IAkish2kJViw5EABaElkcU6cPoSULlbwF5ErmDx2ux/kpU0efP3AFUXVdEFZ3dzBsZFLiuBQAAAABJRU5ErkJggg=="
const arrowUpIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAJZJREFUSEvtk7ENgDAMBD+bMBpMAkwCo8EmYMmRTJTETxFRkDQ07zvzgYDGJzTmowvchv9X0QbgALC63WjgTUUCH3VuYSWswMLj8pSEEeTgtMQTpPBd70C2pyQ1QQ4+KXW+n5SkJKjB4+aUpCSww1JL3Dz9Om1OMpJ9nFpFMjxU4PZNzhxcAt4ls/9TMdcFboW9ou8rugDk6xsZXz5RRwAAAABJRU5ErkJggg==";
class uniformes{
    constructor(equipe, link, temporada, uniforme){
        this.equipe = equipe;
        this.link = link;
        this.temporada = temporada;
        this.uniforme = uniforme;
    }
}

// const options = {
//     mode: 'cors',
//     method: 'GET',
//     headers: {
//         "Accept": "application/json",
//         "Content-Type": "application/json;charset=utf-8"
//         },
//          body: JSON.stringify({time:'',cor:'',liga:''})
//     };

function getService(URL, seletor){  
    let options = {
            mode: 'cors',
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json;charset=utf-8"
                }
            };

    fetch(`${URL}`,options)
        .then((resp) => resp.json())
        .then((data) => criarList(data,seletor))
        .catch(e =>
            console.log(e.message))
}

function request(URL){
    const options = {
        mode: 'cors',
        method: 'GET',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json;charset=utf-8"
            }
        };
    fetch(`${URL}`,options)
    .then(resp =>resp.json())
    .then(data => {
        criarCard(data)
    })
    .catch(e =>
        console.log(e.message))
}

function nomeCores(item){
    let li = document.getElementById(`${item}`);
    li.style.backgroundColor = li.textContent;
    li.addEventListener('click', ()=>{
        let cards = document.querySelectorAll(".card");
        cards.forEach(card =>{
            card.remove();
        })
        request(`http://localhost:8088/LH-PEITAS/base?time&cor=${item}&liga`);
    })
}

function nomeLigas(item){
    let li = document.getElementById(`${item.replace(" ","")}`);
    
    li.addEventListener('click', ()=>{
        let cards = document.querySelectorAll(".card");
        cards.forEach(card =>{
            card.remove();
        })
        request(`http://localhost:8088/LH-PEITAS/base?time&cor&liga=${item}`);
    })

}

//Inicializar 
request('http://localhost:8088/LH-PEITAS/base?time&cor&liga');
getService("http://localhost:8088/LH-PEITAS/listUniformes/cores", "#filtro-listCor-ul");
getService("http://localhost:8088/LH-PEITAS/listarTimes/ligas", "#filtro-listLiga-ul");

// Criar
function criarList(list, seletor){
    const filtroUl = document.querySelector(`${seletor}`);
    list.forEach(
        item =>{
            let li = document.createElement('li')
            let span = document.createElement('span')
            li.setAttribute('id', item.replace(" ",""));
            filtroUl.appendChild(li)
            li.setAttribute("alt", item)
            li.appendChild(span).innerText = item;
            if(seletor =="#filtro-listCor-ul"){
                let input = document.createElement('input')
                input.type = "checkbox"
                input.style.accentColor = item;
                li.appendChild(input)
                nomeCores(item)
            }else if(seletor =="#filtro-listLiga-ul"){
                nomeLigas(item);
            }
        }
        )
        console.log("rodou function");
    }


function criarCard(array){
    array.forEach(element => {
        // clubes = new uniformes(element.nome, element.link, element.temporada, element.uniforme);
        // console.log(clubes);
        let idList = document.querySelector('#list');
        let card = document.createElement('div');
        let equipeDiv = document.createElement('div');
        let temporadaDiv = document.createElement('div');
        let corDiv = document.createElement('div');
        let imagem = document.createElement('img');
        let equipe = document.createElement('span');
        let temporada = document.createElement('span');
        let cor = document.createElement('span');
        
        equipeDiv.appendChild(equipe).innerText = 'Equipe: ' +element.nome.replace(element.nome[0], element.nome[0].toUpperCase());
        temporadaDiv.appendChild(temporada).innerText = 'Temporada: '+ element.temporada.substring(0,4);
        corDiv.appendChild(cor).innerText = 'Nome: '+ element.uniforme;
        card.appendChild(imagem).setAttribute('src', element.link);
        card.appendChild(equipeDiv);
        card.appendChild(temporadaDiv);
        card.appendChild(corDiv);
        card.setAttribute('class','card skeleton');
        idList.appendChild(card);
        function skeleton(){
            const allSkeleton = document.querySelectorAll('.skeleton');
            allSkeleton.forEach(item=>{
                item.classList.remove('skeleton')
            })}
        skeleton();
    });
}
// End Criar

// Skeleton
const allSkeleton = document.querySelectorAll('.skeleton');
	window.addEventListener('load', ()=>{
		allSkeleton.forEach(item=>{
			item.classList.remove('skeleton')
		})
	})
// End Skeleton

// Esconder list
const filtro = document.getElementById('filtro-icon');
filtro.addEventListener("click", ()=>{
    let list = document.querySelector('.filtro-list')
    if(list.getAttribute('hidden') == "false"){
        list.setAttribute('style','display:none')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','true')
    }else{
        list.setAttribute('style','display:flex')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','false')

    }
})

const filtroCor = document.querySelector('.filtro-listCor_content');
filtroCor.addEventListener('click', ()=>{
    let list = document.querySelector('#filtro-listCor-ul');
    let img = document.querySelector("#filtro-listCor-Icon");
    if(list.getAttribute('hidden') == "false"){
        img.removeAttribute('src');
        img.setAttribute('src', arrowDownIcon)
        list.setAttribute('style','display:none');
        list.removeAttribute('hidden');
        list.setAttribute('hidden','true')
    }else{
        img.removeAttribute('src');
        img.setAttribute('src', arrowUpIcon)
        list.setAttribute('style','display:flex');
        list.removeAttribute('hidden');
        list.setAttribute('hidden','false');
        
    }
});

const filtroLiga = document.querySelector('.filtro-listLiga_content');
filtroLiga.addEventListener('click', ()=>{
    let list = document.querySelector('#filtro-listLiga-ul')
    let img = document.querySelector("#filtro-listLiga-Icon");
    if(list.getAttribute('hidden') == "false"){
        img.removeAttribute('src');
        img.setAttribute('src', arrowDownIcon)
        list.setAttribute('style','display:none')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','true')
    }else{
        img.removeAttribute('src');
        img.setAttribute('src', arrowUpIcon)
        list.setAttribute('style','display:block')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','false')
        
    }
});
//

// Buscar por Time
const navSearch = document.querySelector('#nav-search');
navSearch.addEventListener('input' , (e)=>{
    let text = e.target.value;

    const cards = document.querySelectorAll(".card");
    cards.forEach(item =>{
        item.remove();
    })

    request(`http://localhost:8088/LH-PEITAS/base?time=${text.toLowerCase()}&cor&liga`);
})
//
