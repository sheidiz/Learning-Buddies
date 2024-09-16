export const TextInput = ({
  label,
  name,
  placeholder,
  value,
  error,
  onChange,
}) => {
  if (value == null) value = "";
  if (placeholder == null) placeholder = "";

  return (
    <div className="w-full">
      <label htmlFor={name} className="w-full font-semibold">
        {label}
      </label>
      <div className="border-b-2 border-b-dark-grey pb-2 pt-1 dark:border-b-light">
        <input
          type="text"
          name={name}
          id={name}
          placeholder={placeholder}
          defaultValue={value}
          onChange={onChange}
          className="w-full bg-transparent focus-visible:outline-light-green/50 active:outline-light-green/50"
        />
      </div>
      {error && <p className="text-red-600">{error}</p>}
    </div>
  );
};
