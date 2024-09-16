export const TextareaInput = ({
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
      <textarea
        name={name}
        id={name}
        placeholder={placeholder}
        defaultValue={value}
        onChange={onChange}
        className="mt-1 min-h-20 w-full rounded-lg border-2 border-dark-grey bg-transparent p-2 focus-visible:outline-light-green/50 active:outline-light-green/50 dark:border-light"
      ></textarea>
      {error && <p className="text-red-600">{error}</p>}
    </div>
  );
};
