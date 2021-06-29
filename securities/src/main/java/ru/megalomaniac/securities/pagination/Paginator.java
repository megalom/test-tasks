package ru.megalomaniac.securities.pagination;

public interface Paginator {
    // Установить текущую странцу
    public void setCurrentPage(int page);

    // Установить лимит записей на странице
    public void setRecordsOnPageLimit(int limit);

    // Установить лимит страниц выводимых на экран
    public void setPagesLimit(int pagesLimit);

    // Установить количество всех записей
    public void setRecordCount(int recordCount);

    // Получить смещение для запроса из таблицы
    public int getOffset();

    // Получить начало отсчета страниц для вывода на экран
    public int getPageStart();

    // Получить конец отсчета страниц для вывода на экран
    public int getPageEnd();

    // Получить количество страниц для вывода на экран
    public int getPagesLimit();

    // Получить номер текущей страницы
    public int getCurrentPage();

    public int getRecordsOnPageLimit();

}
