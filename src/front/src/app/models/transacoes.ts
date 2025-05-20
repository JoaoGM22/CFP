export class Transacoes {
    id!: number;
    categoria!: string;
    data!: string;
    descricao!: string;
    valor!: number;
    parcelas!:number;
    fixo!: boolean;

    constructor( id: number,
                categoria: string,
                data: string,
                descricao: string,
                valor: number,
                parcelas:number,
                fixo: boolean,)
    {
        this.id = id;
        this.categoria = categoria;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.parcelas = parcelas;
        this.fixo = fixo;

    }
}
