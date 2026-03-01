package org.example.repository;

import org.example.datasource.Skin;

import java.util.List;
import java.util.Optional;

public interface SkinRepository {
        Skin save(Skin skin);
        Optional<Skin> findById(Long id);
        Optional<Skin> findBySkinName(String skinName);
        List<Skin> findAll();
        void update(Skin skin);
        void deleteById(Long id);
        boolean existsBySkinName(String skinName);
}
