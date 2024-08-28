import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;


public class FolderCabinet implements Cabinet {

    private final List<Folder> folders;

    public FolderCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return ofNullable(getFoldersStream())
                .orElseGet(Stream::empty)
                .filter(folder -> Objects.equals(folder.getName(), name))
                .findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return ofNullable(getFoldersStream())
                .orElseGet(Stream::empty)
                .filter(folder -> Objects.equals(folder.getSize(), size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        Stream<Folder> foldersStream = getFoldersStream();
        if (foldersStream == null) {
            return 0;
        }
        return (int) foldersStream.count();
    }

    private Stream<Folder> getFoldersStream() {
        return folders.stream().flatMap(this::flattenFolders);
    }

    private Stream<Folder> flattenFolders(Folder folder) {
        if (folder instanceof MultiFolder) {
            return Stream.concat(Stream.of(folder),
                    ((MultiFolder) folder).getFolders().stream().flatMap(this::flattenFolders));
        } else {
            return Stream.of(folder);
        }
    }
}
