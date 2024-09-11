import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FolderCabinetTest {


    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldReturnEmptyFolder(List<Folder> folders) {
        // given
        FolderCabinet folderCabinet = new FolderCabinet(folders);

        // when
        Optional<Folder> folder = folderCabinet.findFolderByName("notExistedFolder");
        System.out.println(folder);

        // then
        assertTrue(folder.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getFoldersNames")
    void shouldFindFolder(String folderName) {
        // given
        FolderCabinet folderCabinet = new FolderCabinet(getFoldersWithMultiFolders());

        // when
        Optional<Folder> folder = folderCabinet.findFolderByName(folderName);
        System.out.println(folder);

        // then
        assertTrue(folder.isPresent() && folder.get().getName().equals(folderName));
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldReturnEmptyList(List<Folder> folders) {
        // given
        FolderCabinet folderCabinet = new FolderCabinet(folders);

        // when
        List<Folder> foldersBySize = folderCabinet.findFoldersBySize("notExistedSize");
        System.out.println(foldersBySize);

        // then
        assertTrue(foldersBySize.isEmpty());
    }

    @Test
    void shouldFind4FoldersInMEDIUMsize() {
        // given
        FolderCabinet folderCabinet = new FolderCabinet(getFoldersWithMultiFolders());
        List<String> mediumSizeFolders = Arrays.asList("listy", "sprawozdania", "multiFolder1", "czasopisma");

        // when
        List<Folder> foldersBySize = folderCabinet.findFoldersBySize("MEDIUM");
        List<String> foldersNames = foldersBySize.stream().map(Folder::getName).toList();

        // then
        assertEquals(4, foldersBySize.size());
        assertTrue(mediumSizeFolders.containsAll(foldersNames));
    }

    @ParameterizedTest
    @MethodSource("provideEmptyOrNullArguments")
    void shouldReturn0(List<Folder> folders) {
        // given
        FolderCabinet folderCabinet = new FolderCabinet(folders);

        // when
        int count = folderCabinet.count();

        // then
        assertEquals(0, count);
    }

    @Test
    void shouldReturn8() {
        // given
        FolderCabinet folderCabinet = new FolderCabinet(getFoldersWithMultiFolders());

        // when
        int count = folderCabinet.count();

        // then
        assertEquals(8, count);
    }

    public static List<Folder> getFoldersWithMultiFolders() {

        //Utworzenie gabloty z folderami i multifolderami

        List<Folder> folders = new ArrayList<>();

        folders.add(new Folder() {
            @Override
            public String getName() {
                return "listy";
            }

            @Override
            public String getSize() {
                return Size.MEDIUM.getValue();
            }
        });
        folders.add(new Folder() {
            @Override
            public String getName() {
                return "sprawozdania";
            }

            @Override
            public String getSize() {
                return Size.MEDIUM.getValue();
            }
        });

        folders.add(new MultiFolder() {
            @Override
            public String getName() {
                return "multiFolder1";
            }

            @Override
            public String getSize() {
                return Size.MEDIUM.getValue();
            }

            @Override
            public List<Folder> getFolders() {
                List<Folder> folders = new ArrayList<>();
                folders.add(new Folder() {
                    @Override
                    public String getName() {
                        return "książki";
                    }

                    @Override
                    public String getSize() {
                        return Size.SMALL.getValue();
                    }
                });
                folders.add(new Folder() {
                    @Override
                    public String getName() {
                        return "czasopisma";
                    }

                    @Override
                    public String getSize() {
                        return Size.MEDIUM.getValue();
                    }
                });
                return folders;
            }
        });

        folders.add(new MultiFolder() {
            @Override
            public String getName() {
                return "multiFolder2";
            }

            @Override
            public String getSize() {
                return Size.LARGE.getValue();
            }

            @Override
            public List<Folder> getFolders() {
                List<Folder> folders = new ArrayList<>();
                folders.add(new Folder() {
                    @Override
                    public String getName() {
                        return "książki dla dzieci";
                    }

                    @Override
                    public String getSize() {
                        return Size.SMALL.getValue();
                    }
                });
                folders.add(new Folder() {
                    @Override
                    public String getName() {
                        return "czasopisma dziecięce";
                    }

                    @Override
                    public String getSize() {
                        return Size.LARGE.getValue();
                    }
                });
                return folders;
            }
        });
        return folders;
    }

    public static Stream<List<Folder>> provideArguments() {
        return Stream.of(new ArrayList<>(), null, getFoldersWithMultiFolders());
    }

    public static Stream<List<Folder>> provideEmptyOrNullArguments() {
        return Stream.of(new ArrayList<>(), null);
    }

    public static Stream<String> getFoldersNames() {
        return Stream.of("książki dla dzieci", "multiFolder2", "listy");
    }
}