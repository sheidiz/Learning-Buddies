export const TextInput = ({
  label,
  value,
  inputName,
  inputPlaceholder,
  className,
  required,
  onChangeAction,
}) => {
  if (value == null) value = "";
  if (inputPlaceholder == null) inputPlaceholder = "";

  return (
    <div className={`${className} w-full`}>
      <label htmlFor={inputName} className="w-full font-semibold">
        {label}
      </label>
      <div className="border-b-2 border-b-dark-grey pb-2 pt-1 dark:border-b-light">
        <input
          type="text"
          name={inputName}
          id={inputName}
          placeholder={inputPlaceholder}
          required={required}
          onChange={onChangeAction}
          defaultValue={value}
          className="w-full bg-transparent focus-visible:outline-light-green/50 active:outline-light-green/50"
        />
      </div>
    </div>
  );
};
