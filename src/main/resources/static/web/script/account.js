
const urlParams = new URLSearchParams(window.location.search);
const myId = urlParams.get('id');
let URLAPIACC = `http://localhost:8080/api/clients/current`//PARCHE TEMPORAL
Vue.createApp({
    data(){
        return{
            account:{},
            transactions:[],
        }
    },
    created(){
        axios.get(URLAPIACC)
        .then(response => {
            console.log(response)
            this.account = response.data.accounts.filter(acc => acc.id == myId)[0];
            this.transactions = this.account.transactions.sort(function(a, b) {
                return b.id - a.id;
              });
        }).catch(function (error) {
            if (error.response) {
              // La respuesta fue hecha y el servidor respondió con un código de estado
              // que esta fuera del rango de 2xx
              console.log(error.response.data);
              console.log(error.response.status);
              console.log(error.response.headers);
            } else if (error.request) {
              // La petición fue hecha pero no se recibió respuesta
              // `error.request` es una instancia de XMLHttpRequest en el navegador y una instancia de
              // http.ClientRequest en node.js
              console.log(error.request);
            } else {
              // Algo paso al preparar la petición que lanzo un Error
              console.log('Error', error.message);
            }
            console.log(error.config);
          });
    },
    methods:{
        cadenasIguales(string){
            if(string == "CREDITO"){
                return true;
            }
            else
                return false;
                
        },
        async logout(){
            await axios.post('/api/logout').then(response => console.log('signed out!!!'));
            location.href ='http://localhost:8080/web/index.html';
        }
    }
}).mount('#app')