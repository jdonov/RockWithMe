package rockwithme.app.specification;

import org.springframework.data.jpa.domain.Specification;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.PlayerSkills;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSkillsSpecificationBuilder {
    private final List<SearchCriteria> params;

    public PlayerSkillsSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public PlayerSkillsSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<PlayerSkills> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<PlayerSkills>> specs = params.stream()
                .map(PlayerSkillsSpecification::new)
                .collect(Collectors.toList());

        Specification<PlayerSkills> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
