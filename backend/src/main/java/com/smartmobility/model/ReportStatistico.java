package com.smartmobility.model;

import java.time.LocalDateTime;
import java.util.Map;

public class ReportStatistico {
    private String idReport;
    private String criteriAnalisi;
    private LocalDateTime dataGenerazione;
    private Map<String, Object> datiAggregati;

    public ReportStatistico(String idReport, String criteriAnalisi, LocalDateTime dataGenerazione, Map<String, Object> datiAggregati) {
        this.idReport = idReport;
        this.criteriAnalisi = criteriAnalisi;
        this.dataGenerazione = dataGenerazione;
        this.datiAggregati = datiAggregati;
    }

    public String getIdReport() { return idReport; }
    public void setIdReport(String idReport) { this.idReport = idReport; }

    public String getCriteriAnalisi() { return criteriAnalisi; }
    public void setCriteriAnalisi(String criteriAnalisi) { this.criteriAnalisi = criteriAnalisi; }

    public LocalDateTime getDataGenerazione() { return dataGenerazione; }
    public void setDataGenerazione(LocalDateTime dataGenerazione) { this.dataGenerazione = dataGenerazione; }

    public Map<String, Object> getDatiAggregati() { return datiAggregati; }
    public void setDatiAggregati(Map<String, Object> datiAggregati) { this.datiAggregati = datiAggregati; }

    public void esportaFormato() {
        System.out.println("Esportazione del report " + idReport + " in corso...");
        // Logica per esportare il report (es. PDF, CSV) da implementare se necessario
    }
}
