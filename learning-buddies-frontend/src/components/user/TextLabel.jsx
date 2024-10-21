export const TextLabel = ({ label, inputPlaceholder, className }) => {
  if (inputPlaceholder == null || inputPlaceholder.trim().length == 0)
    inputPlaceholder = "-";

  return (
    <div className={`${className} w-full`}>
      <p className="w-full font-semibold">{label}</p>
      <div className="border-b-2 border-b-dark-grey pb-2 pt-1 dark:border-b-light">
        <p className="w-full bg-transparent font-light focus-visible:outline-light-green/50 active:outline-light-green/50">
          {inputPlaceholder}
        </p>
      </div>
    </div>
  );
};
