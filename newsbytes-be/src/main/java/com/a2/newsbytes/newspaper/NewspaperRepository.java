package com.a2.newsbytes.newspaper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {
    Newspaper findByName(String name);

    Newspaper findByType(String type);


}
