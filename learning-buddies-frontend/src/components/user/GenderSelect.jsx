import React from "react";
import Countries from "../../utils/countries.json";

export const GenderSelect = ({ value, onChange, error }) => {
  if (value == null) value = "";

  return (
    <div className="col-span-1 bg-transparent">
      <label htmlFor="gender" className="w-full font-semibold">
        Género
      </label>
      <select
        className="w-full border-b-2 border-b-dark-grey bg-transparent pb-2 pt-1 dark:border-b-light"
        name="gender"
        id="gender"
        onChange={onChange}
        defaultValue={value || "-"}
        required
      >
        <option value="-" disabled>
          Selecciona una opción
        </option>
        <option value="Mujer" className="w-fit text-pretty text-dark-green">
          Mujer
        </option>
        <option value="Hombre" className="w-fit text-pretty text-dark-green">
          Hombre
        </option>
        <option
          value="No binario"
          className="w-fit text-pretty text-dark-green"
        >
          No binario
        </option>
        <option value="Otro" className="w-fit text-pretty text-dark-green">
          Otro
        </option>
        <option
          value="Prefiero no decir"
          className="w-fit text-pretty text-dark-green"
        >
          Prefiero no decir
        </option>
      </select>
      {error && <p className="text-red-600">{error}</p>}
    </div>
  );
};
