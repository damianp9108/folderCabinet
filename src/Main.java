import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

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

        FolderCabinet folderCabinet = new FolderCabinet(folders);


        // Odpytanie użytkownika o nazwę folderu, jaki chce wyszukać

        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz nazwę folderu, który chcesz znaleźć:");
        String inputName = scanner.nextLine();

        Optional<Folder> folderByName = folderCabinet.findFolderByName(inputName);

        if (folderByName.isPresent()) {
            Folder folder = folderByName.get();
            System.out.println("Folder '" + folder.getName() + "' ma rozmiar: " + folder.getSize() + "\n");
        } else {
            System.out.println("Nie znaleziono folderu o nazwie '" + inputName + "'\n");
        }


        // Odpytanie użytkownika o rozmiar folderów, jakie chce wyszukać

        System.out.println("Znajdź foldery o podanym rozmiarze:");
        String inputSize = scanner.nextLine();
        List<Folder> foldersBySize = folderCabinet.findFoldersBySize(inputSize);

        if (!foldersBySize.isEmpty()) {
            System.out.println("Foldery o rozmiarze '" + inputSize + "':");
            int i = 0;
            for (Folder folder : foldersBySize) {
                i = i + 1;
                System.out.println(i + ". " + folder.getName());
            }
        } else {
            System.out.println("Brak folderów o rozmiarze '" + inputSize + "'");
        }

        System.out.println("\nLiczba wszystkich folderów: " + folderCabinet.count());
        scanner.close();
    }
}