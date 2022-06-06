// const urlParams = new URLSearchParams(window.location.search);
// const myId = urlParams.get('id');
// let URLAPI = `http://localhost:8080/api/accounts/${myId}`


let URLAPI = `http://localhost:8080/api/clients/current`

Vue.createApp({
    data() {
        return{
            loading: true,
            client: {},
            accounts:[],
            loans:[],
            
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
                location.href ='http://localhost:8080/web/index.html';
            })
        },
        addAccount(){
            axios.post('/api/clients/current/accounts').then(response =>{
              console.log("cuenta creada");
              location.reload()  
            } )
        },
        fechaDeCreacion(cuenta){
            let fechaDeCreacion = cuenta.creationDate.split("T")[0]
            return fechaDeCreacion;
            
        }

    }
}).mount('#app')