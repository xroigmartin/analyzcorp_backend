package xroigmartin.analyzcorp.investment.infrastructure.out.adapter.sec;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;
import xroigmartin.analyzcorp.investment.domain.model.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SecCompanyJsonParser {

    public List<Company> parseCompanyJsonToListOfCompanies(Map<String, Map<String, Object>> rowData) {
        if(MapUtils.isEmpty(rowData)) return List.of();

        List<Company> companies = new ArrayList<>();

        for(Map.Entry<String, Map<String, Object>> entry : rowData.entrySet()){
            Map<String, Object> data = entry.getValue();

            String cik = String.format("%010d", ((Number) data.get("cik_str")).intValue());
            String name = ((String) data.get("title")).trim();
            String ticker = ((String) data.get("ticker")).trim();

            companies.add(new Company(cik, name, ticker));
        }

        return companies;
    }
}
