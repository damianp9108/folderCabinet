import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FolderCabinet implements Cabinet {

    private final List<Folder> folders;

    public FolderCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return getFoldersStream()
                .filter(folder -> Objects.equals(folder.getName(), name))
                .findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return getFoldersStream()
                .filter(folder -> Objects.equals(folder.getSize(), size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        Stream<Folder> foldersStream = getFoldersStream();
        return (int) foldersStream.count();
    }

    private Stream<Folder> getFoldersStream() {
        return Stream.ofNullable(folders)
                .flatMap(Collection::stream)
                .flatMap(this::flattenFolders);
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
