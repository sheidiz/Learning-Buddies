import React from "react";
import Countries from "../../utils/countries.json";

export const CountrySelector = ({ value, onChangeAction }) => {
  if (value == null) value = "";

  return (
    <div className="col-span-1 bg-transparent">
      <label htmlFor="country" className="w-full font-semibold">
        País
      </label>
      <select
        className="w-full border-b-2 border-b-dark-grey bg-transparent pb-2 pt-1 dark:border-b-light"
        name="country"
        id="country"
        onChange={onChangeAction}
        defaultValue={value || "-"}
        required
      >
        <option value="-" disabled>
          Selecciona una opción
        </option>
        {Countries.map((c) => (
          <option
            key={c.iso2}
            value={c.nameES}
            className="w-fit text-pretty text-dark-green"
          >
            {c.nameES}
          </option>
        ))}
      </select>
    </div>
  );
};
