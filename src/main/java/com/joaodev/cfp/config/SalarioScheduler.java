package com.joaodev.cfp.config;

import com.joaodev.cfp.service.FinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Component
public class SalarioScheduler {

    @Autowired
    private FinanceiroService financeiroService;

    @Scheduled(cron = "0 0 9 * * MON-FRI") // Executa todos os dias úteis às 9:00 AM
    public void atualizarSaldoNoQuintoDiaUtil() {
        LocalDate hoje = LocalDate.now();
        LocalDate quintoDiaUtil = calcularQuintoDiaUtil(hoje);

        if (hoje.equals(quintoDiaUtil)) {
            financeiroService.atualizarSaldoParaTodosUsuarios();
        }
    }

    private LocalDate calcularQuintoDiaUtil(LocalDate date) {
        LocalDate primeiroDiaDoMes = date.with(TemporalAdjusters.firstDayOfMonth());
        int diasUteis = 0;
        LocalDate quintoDiaUtil = primeiroDiaDoMes;

        while (diasUteis < 5) {
            if (!quintoDiaUtil.getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
                    !quintoDiaUtil.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                diasUteis++;
            }
            if (diasUteis < 5) {
                quintoDiaUtil = quintoDiaUtil.plusDays(1);
            }
        }

        return quintoDiaUtil;
    }
}
