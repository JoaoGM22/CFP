import { Routes } from '@angular/router';
import { LoginComponent } from './components/layout/login/login.component';
import { PrincipalComponent } from './components/layout/principal/principal.component';
import { TransacoeslistComponent } from './components/transacoes/transacoeslist/transacoeslist.component';
import { TransacoesdetailsComponent } from './components/transacoes/transacoesdetails/transacoesdetails.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';


export const routes: Routes = [

    {path: "", redirectTo: "login", pathMatch: 'full'},
    {path: "login", component: LoginComponent},
    {path: "admin", component: PrincipalComponent, children:[
        {path: "transacoes", component: TransacoeslistComponent},
        {path: "transacoes/new", component: TransacoesdetailsComponent},
        {path: "transacoes/edit/:id", component: TransacoesdetailsComponent},
        {path: "dashboard", component: DashboardComponent}
    ]},

];
