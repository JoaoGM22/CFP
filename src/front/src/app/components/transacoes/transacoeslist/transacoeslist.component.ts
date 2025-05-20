import { Component, inject, TemplateRef, ViewChild, viewChild } from '@angular/core';
import { Transacoes } from '../../../models/transacoes';
import { RouterLink } from '@angular/router';
import { MdbModalModule, MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import Swal from 'sweetalert2';
import { TransacoesdetailsComponent } from "../transacoesdetails/transacoesdetails.component";
import { TransacaoService } from '../../../services/transacao.service';

@Component({
  selector: 'app-transacoeslist',
  standalone: true,
  imports: [MdbModalModule, TransacoesdetailsComponent],
  templateUrl: './transacoeslist.component.html',
  styleUrl: './transacoeslist.component.scss',
})
export class TransacoeslistComponent {
  lista: Transacoes[] = [];
  transacaoEdit: Transacoes = new Transacoes(0,'','','',0,0,false);

  transacaoServices = inject(TransacaoService)

  modalService = inject(MdbModalService);
  @ViewChild("modalTransacaoDetalhe") modalTransacaoDetalhe!: TemplateRef<any>;
  modalRef!: MdbModalRef<any>;

  constructor() {
    this.findAll();

    //this.deleteByid();


    let transacaoNova = history.state.transacaoNova;
    let transacaoEditada = history.state.transacaoEditada;

    if (transacaoNova) {
      transacaoNova.id = 555;
      this.lista.push(transacaoNova);
    }
    if (transacaoEditada) {
      let indice = this.lista.findIndex((x) => {
        return x.id == transacaoEditada.id;
      });
      this.lista[indice] = transacaoEditada;
    }
  }

  

  findAll(){
    this.transacaoServices.findAll().subscribe({
      next: lista => {
        this.lista = lista;
      },
      error: err => {
        Swal.fire({
          title: 'Ocorreu um erro',
          icon: 'error',
          confirmButtonText: 'Ok'
        })
        console.log(err)
      },
    });
  }


  deleteByid(transacao: Transacoes) {
    Swal.fire({
      title: 'Tem certeza que deseja excluir essa transação!',
      icon: 'warning',
      showDenyButton: true,
      confirmButtonText: 'Sim',
      denyButtonText: 'Não',
    }).then((result) => {
      if (result.isConfirmed) {

        this.transacaoServices.deleteById(transacao.id).subscribe({
          next: mensagem => {

            Swal.fire({
              title: mensagem,
              text: 'Excluído com sucesso!',
              icon: 'success',
              confirmButtonText: 'Ok'
            })
            this.findAll();
          },
          error: err => {
            Swal.fire({
              title: 'Ocorreu um erro',
              icon: 'error',
              confirmButtonText: 'Ok'
            })
          },
        });
      }
    });
  }

  new(){
    this.transacaoEdit =  new Transacoes(0,'','','',0,0,false);;
    this.modalRef = this.modalService.open(this.modalTransacaoDetalhe);
  }

  edit(transacao:Transacoes){
    this.transacaoEdit = Object.assign({}, transacao);
    this.modalRef = this.modalService.open(this.modalTransacaoDetalhe);
  }

  retornoDetalhe(transacao: Transacoes){

    this.findAll();
    this.modalRef.close();
  }

}
