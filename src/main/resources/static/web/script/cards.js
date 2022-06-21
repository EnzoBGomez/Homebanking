let URLAPI = `http://localhost:8080/api/clients/current/cards`

Vue.createApp({
    data() {
        return{
            flip: false,
            loading: true,
            client:{},
            cards:[],
            arrayNumberCard:[],
            debitCards: [],
            creditCards: [],
            dateToday:'',
            dateThru: '',
        }
    },
    async created(){
        await axios.get(URLAPI)
        .then(response => {
            
            console.log(response)
            
            this.cards = response.data;
            this.debitCards = this.cards.filter(card => card.type == 'DEBIT')
            this.creditCards = this.cards.filter(card => card.type == 'CREDIT')
        })
        
        this.clienteLogueado()
        this.loading = false;
    },
    methods:{
        async clienteLogueado(){
            await axios.get("http://localhost:8080/api/clients/current")
            .then(response => {
                this.client = response.data;
            })
        },
        separarNumerosCard(card){
            var res = [...card].reduce((p, c, i) => p += (i && !(i % 4)) ? "-" + c : c, "").split('-');

            return res;
        },
        getExpiresDate(dateInput){
            const date = new Date(dateInput)
            const dateYear = date.getFullYear()
            const getTwoDigits = dateYear.toString().substring(2)
            return (date.getMonth() + 1) + "/" + getTwoDigits 

            return this.dateThru
        },
        async logout(){
            console.log("asd")
            await axios.post('/api/logout').then(response => console.log('signed out!!!'));
            location.href ='http://localhost:8080/web/index.html';
        },
        toCreateCard(){
            location.href='http://localhost:8080/web/create-cards.html';
        },
        async deleteCard(idCard){
            await axios.patch('/api/clients/current/cards',`idCard=${idCard}`).then(response => {
                console.log(response)
            })
            location.href='http://localhost:8080/web/cards.html'
        },
        // currentDate(){
        //     const date = new Date()
        //     const dateYear = date.getFullYear()
        //     const getTwoDigits = dateYear.toString().substring(2)
        //     //console.log(((date.getMonth() + 1) + "/" + getTwoDigits) == getExpiresDate(card.thruDate))
        //     this.dateToday =  ((date.getMonth() + 1) + "/" + getTwoDigits)
        //     console.log(this.dateToday)
        //     return this.dateToday
        // }
    }
}).mount('#app')