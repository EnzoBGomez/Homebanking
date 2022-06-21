let URLAPI = `http://localhost:8080/api/clients/current/accounts`

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
            montoATransferir: 0,
            descripcionTransferencia:"",

        }
    },
    async created(){
        await axios.get(URLAPI)
        .then(response => {
            console.log(response)
            
            this.cuentas = response.data;
        })

        this.cargarCliente();
        this.loading = false;
    },
    methods:{
        async cargarCliente() {
            await axios.get("/api/clients/current")
                .then(response => {
                    this.client = response.data
                    console.log(response.data)
                })
        },
        async logout(){
            console.log("asd")
            await axios.post('/api/logout').then(response => console.log('signed out!!!'));
            location.href ='http://localhost:8080/web/index.html';
        },
        cuentasDestinoFiltro(){
            return this.cuentas.filter(cuenta => cuenta.number != this.cuentaDeOrigen);
        },
        // cuentasIguales(){
        //     let numerosCuentas = this.cuentas.map(cuenta => cuenta.number)
        //     console.log(numerosCuentas)
        //     console.log(this.numeroDeCuentaDestino)
        //     console.log(numerosCuentas.includes(this.numeroDeCuentaDestino))
            
        //     return numerosCuentas.includes(this.numeroDeCuentaDestino)
        // }
        cuentaSeleccionada(){
            return this.cuentas.filter(cuenta => cuenta.number == this.cuentaDeOrigen)[0]
        },
        // validarDato(){
        //     return (this.montoATransferir === 0 && this.numeroDeCuentaDestino.length != 12)
        // }
        abrirModal(){
            $('#modalTransfer').modal('show'); // abrir
        },
        transaccion(){
            axios.post('/api/transactions',`amount=${this.montoATransferir}&description=${this.descripcionTransferencia}&numberOriginAccount=${this.cuentaDeOrigen}&numberDestinyAccount=${this.numeroDeCuentaDestino}`)
            .then(response => console.log(response));
            location.href='http://localhost:8080/web/accounts.html'
        }

        
    }
}).mount('#app')