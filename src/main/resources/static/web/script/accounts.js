// const urlParams = new URLSearchParams(window.location.search);
// const myId = urlParams.get('id');
// let URLAPI = `http://localhost:8080/api/accounts/${myId}`


let URLAPI = `/api/clients/current`

Vue.createApp({
    data() {
        return{
            loading: true,
            client: {},
            accounts:[],
            loans:[],
            tipoDeCuenta:'',
            
        }
    },
    async created(){
        await axios.get(URLAPI)
        .then(response => {
            console.log(response)

            this.client = response.data;
            this.accounts = this.client.accounts;
            this.loans = this.client.loans;
        })

        this.loading = false;
    },
    methods:{
        async logout(){
            //console.log("asd")
            await axios.post('/api/logout').then(response => {
                console.log('signed out!!!');
                location.href ='/web/index.html';
            })
        },
        async addAccount(){
            await axios.post('/api/clients/current/accounts',`typeAccount=${this.tipoDeCuenta}`).then(response =>{
              console.log("cuenta creada");
              location.reload()  
            } )
        },
        fechaDeCreacion(cuenta){
            let fechaDeCreacion = cuenta.creationDate.split("T")[0]
            return fechaDeCreacion;
            
        },
        abrirModalCrearCuenta(){
            $('#modalCreateAccount').modal('show'); // abrir
        },
        abrirModalPedirPrestamo(){
            $('#modalLoan').modal('show'); // abrir
        },
        redirigirALoan(){
            location.href = '/web/loan-application.html'
        },
        deleteAccount(number){
            axios.patch('/api/clients/current/accounts',`number=${number}`).then(response => {
                location.href='/web/accounts.html'
            })

        }
    }
}).mount('#app')