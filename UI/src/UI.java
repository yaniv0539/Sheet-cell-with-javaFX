import engine.api.Engine;
import engine.impl.EngineImpl;
import engine.version.manager.api.VersionManagerGetters;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;

import java.util.InputMismatchException;
import java.util.Scanner;

public enum UI {

    MAIN_MENU("Main Menu", null) {
        @Override
        void execute(Engine engine) {
            runMenu(MAIN_MENU);
        }
    },
    LOAD_XML_FILE("Load XML File", MAIN_MENU) {
        @Override
        void execute(Engine engine) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the filename to load: ");
            String filename = scanner.nextLine();
            engine.readXMLInitFile(filename);
            runMenu(SECOND_MENU);
        }
    },
    EXIT("Exit", MAIN_MENU) {
        @Override
        void execute(Engine engine) {
            engine.exit();
            System.exit(0);
        }
    },
    SECOND_MENU("Operations", LOAD_XML_FILE) {
        @Override
        void execute(Engine engine) {
            runMenu(SECOND_MENU);
        }
    },
    SHOW_SHEET("Show Sheet", SECOND_MENU) {
        @Override
        void execute(Engine engine) {
            printSheet(engine.getSheetStatus());
        }
    },
    SHOW_CELL("Show Cell", SECOND_MENU) {
        @Override
        void execute(Engine engine) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the wanted cell you want to show: ");
            String cellName = scanner.nextLine();
            printFullCellStatus(cellName, engine.getCellStatus(cellName));
        }
    },
    UPDATE_CELL("Update cell", SECOND_MENU) {
        @Override
        void execute(Engine engine) {
            Scanner scanner = new Scanner(System.in);
            String cellName;

            while (true) {
                try {
                    System.out.print("Enter the cell you want to update: ");
                    cellName = scanner.nextLine();
                    printCellStatus(cellName, engine.getCellStatus(cellName));
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage() + ". Please try again.");
                }
            }

            while (true) {
                try {
                    System.out.print("Enter the cell new value: ");
                    String value = scanner.nextLine();
                    engine.updateCellStatus(cellName, value);
                    System.out.println("Cell " + cellName + " updated successfully.");
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage() + ". Please try again.");
                }
            }

        }
    },
    SHOW_VERSIONS("Show Versions", SECOND_MENU) {
        @Override
        void execute(Engine engine) {
            VersionManagerGetters versionManager = engine.getVersionsManagerStatus();
            printVersionsTable(versionManager);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                try {
                    System.out.println("Enter the version you want to show: ");
                    int version = scanner.nextInt();
                    printSheet(versionManager.getVersion(version));
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input: please enter an integer that represent the version you want to show.");
                    scanner.nextLine();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage() + ". Please try again.");
                }
            }
        }
    },
    BACK("Go Back", null) {
        @Override
        void execute(Engine engine) {
            // Go back is handled in the menu runner
        }
    };

    private final String description;
    private final UI parentMenu;
    private static Engine engine;

    UI(String description, UI parentMenu) {
        this.description = description;
        this.parentMenu = parentMenu;
    }

    private String getDescription() {
        return description;
    }

    private UI getParentMenu() {
        return parentMenu;
    }

    abstract void execute(Engine engine);

    public static void run() {
        engine = EngineImpl.create();
        UI.runMenu(UI.MAIN_MENU);
    }

    private static void runMenu(UI menu) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n--- " + menu.getDescription() + " ---");
                int optionNumber = 1;
                for (UI option : UI.values()) {
                    if (option.getParentMenu() == menu) {
                        System.out.println(optionNumber++ + ". " + option.getDescription());
                    }
                }
                if (menu != MAIN_MENU) {
                    System.out.println(optionNumber + ". Go Back");
                }

                System.out.print("Please select an option: ");
                int choice = scanner.nextInt();

                    int validOptionsCount = 0;
                    for (UI option : UI.values()) {
                        if (option.getParentMenu() == menu) {
                            validOptionsCount++;
                            if (choice == validOptionsCount) {
                                option.execute(engine);
                                break;
                            }
                        }
                    }

                    if (menu != MAIN_MENU && choice == validOptionsCount + 1) {
                        break; // Go back to the parent menu
                    } else if (choice > validOptionsCount + (menu == MAIN_MENU ? 0 : 1) || choice < 1) {
                        System.out.println("Invalid selection, please try again.");
                    }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input: please enter a number corresponding to the menu option.");
                scanner.nextLine();
            }
            catch (Exception e) {
                System.out.println(e.getMessage() + ". Please try again.");
                scanner.nextLine();
            }
        }
    }

    private static void printSheet(SheetGetters sheet) {

    }

    private static void printFullCellStatus(String cellName, CellGetters cell) {
        printCellStatus(cellName, cell);
        StringBuilder sb = new StringBuilder();

        if (cell == null) {
            sb.append("Version: 1\nDepends on: []\nInfluence on: []");
        }

        else {
            sb.append("Version: ").append(cell.getVersion())
                    .append("\nDepends on: [");
            cell.getInfluenceFrom().forEach(cellDep -> sb.append(cellDep.getCoordinate()).append(", "));
            sb.deleteCharAt(sb.length() - 2).append("]")
                    .append("\nInfluence on: [");
            cell.getInfluenceOn().forEach(cellInf -> sb.append(cellInf.getCoordinate()).append(", "));
            sb.deleteCharAt(sb.length() - 2).append("]");
        }

        System.out.println(sb.toString());
    }

    private static void printCellStatus(String cellName, CellGetters cell) {
        StringBuilder sb = new StringBuilder();

        if (cell == null) {
            sb.append("Cell ID: ")
                    .append(cellName)
                    .append("\nOriginal Value: ")
                    .append("\nEffective Value:");
        } else {
            sb.append("Cell ID: ").append(cellName)
                    .append("\nOriginal Value: ").append(cell.getOriginalValue())
                    .append("\nEffective Value: ").append(cell.getEffectiveValue().getValue());
        }

        System.out.println(sb.toString());
    }

    private static void printVersionsTable(VersionManagerGetters versionsManagerStatus) {

    }
}
