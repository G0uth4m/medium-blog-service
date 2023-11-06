package com.goutham.mediumblogservice.repository;

import com.goutham.mediumblogservice.entity.Clap;
import com.goutham.mediumblogservice.entity.key.ClapKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClapRepository extends JpaRepository<Clap, ClapKey> {

}
