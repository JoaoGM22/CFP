// src/app/models/financeiro-dto.model.ts

export interface Usuario {
    id?: number;
    nome?: string;
    email?: string;
    senha?: string;
    role?: string;
}

export class Contas {
  id!: number;
  descricao!: string;
  valor!: number;
  data!: string;

  constructor(
    id: number,
    descricao: string,
    valor: number,
    data: string
  ) {
    this.id = id;
    this.descricao = descricao;
    this.valor = valor;
    this.data = data;
  }
}

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

export interface FinanceiroDTO {
  saldo: number;
  despesaMensal: number;
  rendaMensal: number;
  transacoesRecentes: Transacoes[];
  contas: Contas[];
  usuario: Usuario;
}

export const FINANCEIRO_DTO_INICIAL: FinanceiroDTO = {
  saldo: 0,
  despesaMensal: 0,
  rendaMensal: 0,
  transacoesRecentes: [],
  contas: [],
  usuario: {}
};