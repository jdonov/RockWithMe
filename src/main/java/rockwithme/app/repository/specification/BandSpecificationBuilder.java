package rockwithme.app.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import rockwithme.app.model.entity.Band;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BandSpecificationBuilder {
    private final List<SearchCriteria> params;

    public BandSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public BandSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Band> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Band>> specs = params.stream()
                .map(BandSpecification::new)
                .collect(Collectors.toList());

        Specification<Band> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
