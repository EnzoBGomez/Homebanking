let URLAPI = `http://localhost:8080/api/clients/current`

Vue.createApp({
    data() {
        return{
            loading: true,
            client:{},
            cuentas:[],
            cuentaDeOrigen:'',
            cuentaDeDestino:'',
            transferirA:'',
            numeroDeDestino:'',
            numeroDeCuentaDestino:'',

        }
    },
    async created(){
        await axios.get(URLAPI)
        .then(response => {
            console.log(response)
            this.client = response.data;
            this.cuentas = response.data.accounts;
        })
        this.loading = false;
    },
    methods:{
        async logout(){
            console.log("asd")
            await axios.post('/api/logout').then(response => console.log('signed out!!!'));
            location.href ='http://localhost:8080/web/index.html';
        },
        cuentasDestinoFiltro(){
            return this.cuentas.filter(cuenta => cuenta.number != this.cuentaDeOrigen);
        }
        
    }
}).mount('#app')