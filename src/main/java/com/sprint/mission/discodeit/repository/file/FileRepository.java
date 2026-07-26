package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Entity;
import com.sprint.mission.discodeit.repository.Repository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


public class FileRepository<T extends Entity> implements Repository<T> {

   private final String fileName;

   protected FileRepository(String fileName) {
       this.fileName = fileName + ".ser";

       File file = new File(this.fileName);
       if (!file.exists()) {
           writeData(new LinkedHashMap<>());
       }
   }

   private Map<UUID, T> readData() {
       try (
           FileInputStream fis = new FileInputStream(this.fileName);
           ObjectInputStream ois = new ObjectInputStream(fis)
           ) {
           return (Map<UUID, T>) ois.readObject();
       } catch (IOException | ClassNotFoundException exception) {
           throw new RuntimeException("파일 불러오기가 실패했습니다." + exception.getMessage());

       }
   }

   private void writeData(Map<UUID, T> data) {
       try (
           FileOutputStream fos = new FileOutputStream(this.fileName);
           ObjectOutputStream oos = new ObjectOutputStream(fos)
       ) {
           oos.writeObject(data);
       } catch (IOException exception) {
           throw new RuntimeException("저장에 실패하였습니다." + exception.getMessage());
       }
   }



    @Override
    public T save(T entity) {
       Map<UUID, T> data = readData();
       data.put(entity.getId(),entity);
       writeData(data);
        return entity;
    }

    @Override
    public Optional<T> findById(UUID id) {
       Map<UUID, T> data = readData();
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<T> findAll() {
       Map<UUID, T> data = readData();
        return data.values().stream().collect(Collectors.toList());
    }

    @Override
    public T update(T entity) {
       Map<UUID, T> data = readData();
       data.put(entity.getId(), entity);
       writeData(data);
        return entity;
    }

    @Override
    public void delete(UUID id) {
       Map<UUID, T> data = readData();
       data.remove(id);
       writeData(data);
    }
}
