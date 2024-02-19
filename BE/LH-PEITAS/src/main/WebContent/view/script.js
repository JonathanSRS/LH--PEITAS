const BASE_URL = "http://localhost:8088/LH-PEITAS"
const arrowDownIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAJxJREFUSEvtk9ENgCAMRI9NHE0nUSfR0RxFm0jSIO1VE34MfB/36AMSGq/UuB8dQA3/X9EA4KAenICnaLvLVwJwcxZANo138QLAgtCcBZivcinOqwbR5ZLbAUzltJ4iDxIqFxh7pjWIXHzWZ548T8IAkish2kJViw5EABaElkcU6cPoSULlbwF5ErmDx2ux/kpU0efP3AFUXVdEFZ3dzBsZFLiuBQAAAABJRU5ErkJggg=="
const arrowUpIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAJZJREFUSEvtk7ENgDAMBD+bMBpMAkwCo8EmYMmRTJTETxFRkDQ07zvzgYDGJzTmowvchv9X0QbgALC63WjgTUUCH3VuYSWswMLj8pSEEeTgtMQTpPBd70C2pyQ1QQ4+KXW+n5SkJKjB4+aUpCSww1JL3Dz9Om1OMpJ9nFpFMjxU4PZNzhxcAt4ls/9TMdcFboW9ou8rugDk6xsZXz5RRwAAAABJRU5ErkJggg==";
const allSkeleton = document.querySelectorAll('.skeleton');
const navSearch = document.querySelector('#nav-search');
const filtro = document.getElementById('filtro-icon');
const filtroCor = document.querySelector('.filtro-listCor_content');
const filtroLiga = document.querySelector('.filtro-listLiga_content');
class camisas{
    constructor(data){
        this.equipe = data.nome;
        this.link = data.link;
        this.temporada = data.temporada;
        this.uniforme = data.uniforme;
    }
    criarCard(){
        let idList = document.querySelector('#list');
        let card = document.createElement('div');
        let equipeDiv = document.createElement('div');
        let temporadaDiv = document.createElement('div');
        let corDiv = document.createElement('div');
        let imagem = document.createElement('img');
        let equipe = document.createElement('span');
        let temporada = document.createElement('span');
        let cor = document.createElement('span');
        
        equipeDiv.appendChild(equipe).innerText = 'Equipe: ' +this.equipe.replace(this.equipe[0], this.equipe[0].toUpperCase());
        temporadaDiv.appendChild(temporada).innerText = 'Temporada: '+ this.temporada.substring(0,4);
        corDiv.appendChild(cor).innerText = 'Nome: '+ this.uniforme;
        card.appendChild(imagem).setAttribute('src', this.link);
        card.appendChild(equipeDiv);
        card.appendChild(temporadaDiv);
        card.appendChild(corDiv);
        card.setAttribute('class','card skeleton');
        idList.appendChild(card);
        let allSkeleton = document.querySelectorAll('.skeleton');
        function skeleton(){
            allSkeleton.forEach(item=>{
                item.classList.remove('skeleton')
            })}
        skeleton();
    }
}

// const options = {
//     mode: 'cors',
//     method: 'POST',
//     headers: {
//         "Accept": "application/json",
//         "Content-Type": "application/json;charset=utf-8"
//         },
//          body: JSON.stringify({time:'',cor:'',liga:''})
//     };

// Create Functions
function criarList(list, seletor){
    let filtroUl = document.querySelector(`${seletor}`);
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

function nomeCores(item){
    let li = document.getElementById(`${item}`);
    li.style.backgroundColor = li.textContent;
    li.addEventListener('click', ()=>{
        let cards = document.querySelectorAll(".card");
        cards.forEach(card =>{
            card.remove();
        })
        Promise.resolve(request(`${BASE_URL}/base?time&cor=${item}&liga`))
        .then(data =>{data.forEach(i=>{
                new camisas(i).criarCard()
            })
        })
    })
}

function nomeLigas(item){
    let li = document.getElementById(`${item.replace(" ","")}`);
    li.addEventListener('click', ()=>{
        let cards = document.querySelectorAll(".card");
        cards.forEach(card =>{
            card.remove();
        })
        Promise.resolve(request(`${BASE_URL}/base?time&cor&liga=${item}`))
        .then(data =>{data.forEach(i=>{
                new camisas(i).criarCard()
            })
        });
    })

}
// Requests functions
function request(URL){
    const options = {
        mode: 'cors',
        method: 'GET',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json;charset=utf-8"
            }
        };
    return fetch(`${URL}`,options)
    .then(resp =>resp.json())
    .catch(e =>
        console.log(e.message));
} // END Requests functions
// Hidden list
function hidden(display, element){
    if(element.getAttribute('hidden') == "false"){
        element.setAttribute('style','display:none')
        element.removeAttribute('hidden');
        element.setAttribute('hidden','true')
    }else{
        element.setAttribute('style',`display:${display}`)
        element.removeAttribute('hidden');
        element.setAttribute('hidden','false')
    }
}

function changeArrow(boolean, icon){
    if(boolean == "false"){
        icon.removeAttribute('src');
        icon.setAttribute('src', arrowUpIcon)
    }else{
        icon.removeAttribute('src');
        icon.setAttribute('src', arrowDownIcon)
    }
}// END Hidden list
// END Create

//Initialize 
Promise.resolve(request(BASE_URL+'/base?time&cor&liga'))
.then(data =>{data.forEach(item=>{
        new camisas(item).criarCard()
    })
});
Promise.resolve(request(BASE_URL+"/listUniformes/cores")).then(data=>{
    criarList(data,"#filtro-listCor-ul")
})
Promise.resolve(request(BASE_URL+"/listarTimes/ligas")).then(data=>{
    criarList(data,"#filtro-listLiga-ul")
})
// END Initialize

// Events
// Skeleton
window.addEventListener('load', ()=>{
	allSkeleton.forEach(item=>{
		item.classList.remove('skeleton')
	})
})// End Skeleton

filtro.addEventListener("click", ()=>{
    let list = document.querySelector('.filtro-list')
    hidden("flex",list);
})

filtroCor.addEventListener('click', ()=>{
    let list = document.querySelector('#filtro-listCor-ul');
    let img = document.querySelector("#filtro-listCor-Icon");
    hidden("flex",list);
    changeArrow(list.getAttribute('hidden'),img)
});

filtroLiga.addEventListener('click', ()=>{
    let list = document.querySelector('#filtro-listLiga-ul')
    let img = document.querySelector("#filtro-listLiga-Icon");
    hidden("block",list);
    changeArrow(list.getAttribute('hidden'),img);
});

navSearch.addEventListener('input' , (e)=>{
    let text = e.target.value;
    const cards = document.querySelectorAll(".card");
    cards.forEach(item =>{
        item.remove();
    })
    // request(`${BASE_URL}/base?time=${text.toLowerCase()}&cor&liga`);
    Promise.resolve(request(`${BASE_URL}/base?time=${text.toLowerCase()}&cor&liga`))
    .then(data =>{
        data.forEach(item=>{
            new camisas(item).criarCard()
        })
    })
})
// END Events