import { useMemo, useState } from "react";
import { Search, ChevronLeft, ChevronRight, ArrowUpDown } from "lucide-react";

export default function DataTable({
  title,
  columns,
  data = [],
  loading = false,
}) {
  const [search, setSearch] = useState("");
  const [sortKey, setSortKey] = useState(null);
  const [sortDirection, setSortDirection] = useState("asc");
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(5);

  const filteredData = useMemo(() => {
    const searchLower = search.toLowerCase();

    const filtered = data.filter((item) =>
      columns.some((column) => {
        const value = column.accessor(item);
        return String(value ?? "")
          .toLowerCase()
          .includes(searchLower);
      }),
    );

    if (sortKey) {
      const column = columns.find((column) => column.key === sortKey);

      if (column) {
        filtered.sort((a, b) => {
          const valueA = String(column.accessor(a) ?? "");
          const valueB = String(column.accessor(b) ?? "");

          const result = valueA.localeCompare(valueB, "pt-BR", {
            numeric: true,
          });

          return sortDirection === "asc" ? result : -result;
        });
      }
    }

    return filtered;
  }, [data, columns, search, sortKey, sortDirection]);

  const totalPages = Math.max(1, Math.ceil(filteredData.length / pageSize));

  const currentPage = Math.min(page, totalPages);

  const paginatedData = filteredData.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize,
  );

  function handleSort(key) {
    if (sortKey === key) {
      setSortDirection((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortKey(key);
      setSortDirection("asc");
    }
  }

  return (
    <div className="w-full max-w-5xl rounded-2xl bg-white p-6 shadow-lg">
      <div className="mb-6 flex flex-wrap items-center justify-between gap-4">
        <div>
          <h2 className="text-xl font-semibold text-gray-800">{title}</h2>
          <p className="text-sm text-gray-500">
            {filteredData.length} registro(s) encontrado(s)
          </p>
        </div>

        <div className="relative w-full max-w-xs">
          <Search
            size={18}
            className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
          />

          <input
            type="text"
            value={search}
            onChange={(e) => {
              setSearch(e.target.value);
              setPage(1);
            }}
            placeholder="Pesquisar..."
            className="w-full rounded-lg border border-gray-300 py-3 pl-10 pr-3 text-gray-900 outline-none focus:border-blue-500"
          />
        </div>
      </div>

      <div className="w-full overflow-x-auto">
        <table className="w-full border-collapse text-left">
          <thead className="bg-gray-100">
            <tr>
              {columns.map((column) => (
                <th
                  key={column.key}
                  className="px-4 py-3 text-sm font-semibold text-gray-700"
                >
                  <button
                    type="button"
                    onClick={() => handleSort(column.key)}
                    className="flex cursor-pointer items-center gap-2"
                  >
                    {column.header}
                    <ArrowUpDown size={14} />
                  </button>
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td
                  colSpan={columns.length}
                  className="px-4 py-8 text-center text-gray-500"
                >
                  Carregando registros...
                </td>
              </tr>
            ) : paginatedData.length === 0 ? (
              <tr>
                <td
                  colSpan={columns.length}
                  className="px-4 py-8 text-center text-gray-500"
                >
                  Nenhum registro encontrado.
                </td>
              </tr>
            ) : (
              paginatedData.map((item, index) => (
                <tr
                  key={item.id ?? item.matricula ?? index}
                  className="border-b border-gray-100 transition hover:bg-gray-50"
                >
                  {columns.map((column) => (
                    <td
                      key={column.key}
                      className="px-4 py-3 text-sm text-gray-700"
                    >
                      {column.accessor(item) ?? "—"}
                    </td>
                  ))}
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      <div className="mt-5 flex flex-wrap items-center justify-between gap-4">
        <select
          value={pageSize}
          onChange={(e) => {
            setPageSize(Number(e.target.value));
            setPage(1);
          }}
          className="rounded-lg border border-gray-300 p-2 text-sm text-gray-700"
        >
          <option value={5}>5 por página</option>
          <option value={10}>10 por página</option>
          <option value={20}>20 por página</option>
        </select>

        <div className="flex items-center gap-3">
          <button
            type="button"
            disabled={currentPage === 1}
            onClick={() => setPage((prev) => Math.max(1, prev - 1))}
            className="rounded-lg border border-gray-300 p-2 disabled:opacity-40"
          >
            <ChevronLeft size={18} />
          </button>

          <span className="text-sm text-gray-600">
            {currentPage} de {totalPages}
          </span>

          <button
            type="button"
            disabled={currentPage === totalPages}
            onClick={() => setPage((prev) => Math.min(totalPages, prev + 1))}
            className="rounded-lg border border-gray-300 p-2 disabled:opacity-40"
          >
            <ChevronRight size={18} />
          </button>
        </div>
      </div>
    </div>
  );
}
