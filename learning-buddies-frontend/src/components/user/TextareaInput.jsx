export const TextareaInput = ({
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
      <textarea
        name={inputName}
        id={inputName}
        placeholder={inputPlaceholder}
        required={required}
        onChange={onChangeAction}
        defaultValue={value}
        className="mt-1 min-h-20 w-full rounded-lg border-2 border-dark-grey bg-transparent p-2 focus-visible:outline-light-green/50 active:outline-light-green/50 dark:border-light"
      ></textarea>
    </div>
  );
};
