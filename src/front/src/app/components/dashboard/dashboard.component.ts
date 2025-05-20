// src/app/components/dashboard/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { FinanceiroDTO } from '../../models/FinanceiroDTO';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatListModule,
    MatProgressSpinnerModule,
    MatButtonModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  dashboardData!: FinanceiroDTO;
  loading = true;
  error = false;
  today = new Date();

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData() {
    this.dashboardService.getHomeData().subscribe(
      data => {
        this.dashboardData = data;
        this.loading = false;
      },
      error => {
        console.error('Erro ao carregar dados do dashboard', error);
        this.error = true;
        this.loading = false;
      }
    );
  }
}