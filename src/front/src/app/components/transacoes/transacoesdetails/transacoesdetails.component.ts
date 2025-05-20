import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { FormsModule } from '@angular/forms';
import { Transacoes } from '../../../models/transacoes';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { TransacaoService } from '../../../services/transacao.service';

@Component({
  selector: 'app-transacoesdetails',
  standalone: true,
  imports: [MdbFormsModule,FormsModule],
  templateUrl: './transacoesdetails.component.html',
  styleUrl: './transacoesdetails.component.scss'
})
export class TransacoesdetailsComponent {

  @Input("transacao") transacao: Transacoes = new Transacoes(0,'','','',0,0,false);
  @Output("retorno") retorno = new EventEmitter<any>();
  router = inject(ActivatedRoute);
  router2 = inject(Router);
  transacaoServices = inject(TransacaoService);

  constructor(){
    let id = this.router.snapshot.params['id']
    if(id > 0){
      this.findById(id);
    }
  }

  findById(id: number){
    this.transacaoServices.findByid(id).subscribe({
          next: retorno => {
            this.transacao = retorno;
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

    salvar(){
      if(this.transacao.id > 0){

        this.transacaoServices.update(this.transacao).subscribe({
          next: mensagem => {
            Swal.fire({
              title: mensagem,
              icon: 'success',
              confirmButtonText: 'Ok'
            })
            this.router2.navigate(['admin/transacoes'],{state: { transacaoEditada: this.transacao}})
            this.retorno.emit(this.transacao);
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

      }else{

        this.transacaoServices.save(this.transacao).subscribe({
          next: mensagem => {
            Swal.fire({
              title: mensagem,
              icon: 'success',
              confirmButtonText: 'Ok'
            })
            this.router2.navigate(['admin/transacoes'],{state: { transacaoNova: this.transacao}})
            this.retorno.emit(this.transacao);
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
    }
}
