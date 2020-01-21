package jun.projavawebapp.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public interface SearchCriteria {

    class Builder {

        private List<Criterion> criterions = new ArrayList<>();

        public void addCriterion(Criterion criterion) {
            criterions.add(criterion);
        }

        public SearchCriteria build() {
            return new SearchCriteriaImpl(this.criterions);
        }

        private static class SearchCriteriaImpl implements SearchCriteria {

            private Collection<Criterion> criterions;

            public SearchCriteriaImpl(Collection<Criterion> criterions) {
                this.criterions = Collections.unmodifiableCollection(criterions);
            }

            @Override
            public Stream<Criterion> stream() {
                return this.criterions.stream();
            }
        }
    }

    Stream<Criterion> stream();
}
