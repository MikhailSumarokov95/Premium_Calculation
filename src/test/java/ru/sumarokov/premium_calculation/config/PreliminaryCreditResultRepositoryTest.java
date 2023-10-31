package ru.sumarokov.premium_calculation.config;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.sumarokov.premium_calculation.entity.PreliminaryCreditResult;
import ru.sumarokov.premium_calculation.repository.PreliminaryCreditResultRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

//public class PreliminaryCreditResultRepositoryTest implements PreliminaryCreditResultRepository {
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<PreliminaryCreditResult> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public PreliminaryCreditResult getOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public PreliminaryCreditResult getById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public PreliminaryCreditResult getReferenceById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends PreliminaryCreditResult> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<PreliminaryCreditResult> findById(Long aLong) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public List<PreliminaryCreditResult> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<PreliminaryCreditResult> findAllById(Iterable<Long> longs) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(PreliminaryCreditResult entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends PreliminaryCreditResult> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<PreliminaryCreditResult> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<PreliminaryCreditResult> findAll(Pageable pageable) {
//        return null;
//    }
//}
