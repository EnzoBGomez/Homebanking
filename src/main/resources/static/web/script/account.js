
const urlParams = new URLSearchParams(window.location.search);
const myId = urlParams.get('id');
let URLAPIACC = `/api/clients/current/accounts/${myId}`
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
            this.account = response.data
            this.transactions = this.account.transactions.sort(function(a, b) {
                return b.id - a.id;
              });
        }).catch(function (error) {
            if (error.response) {
              console.log(error.response.data);
              console.log(error.response.status);
              console.log(error.response.headers);
            } else if (error.request) {
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
            location.href ='/web/index.html';
        },
        fechaDeCreacion(fecha){
            let fechaDeCreacion = fecha.split("T")[0]
            return fechaDeCreacion;
            
        },
    }
}).mount('#app')