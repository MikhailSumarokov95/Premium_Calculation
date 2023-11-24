package ru.sumarokov.premium_calculation.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(Class<?> classEntity, Long id) {
        super(String.format("Объект класса %s c id = %d не найден", classEntity.getName(), id));
    }

    public EntityNotFoundException(Class<?> classEntity) {
        super(String.format("Объект класса %s не найден", classEntity.getName()));
    }
}
