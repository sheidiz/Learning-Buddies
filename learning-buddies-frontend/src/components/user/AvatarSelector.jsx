import { useEffect, useState } from "react";
import { MdChevronLeft, MdChevronRight } from "react-icons/md";
import { images } from "../../utils/examples";

const AvatarSelector = ({
  currentIndex,
  onImageSelect,
  bgColor,
  setBgColor,
}) => {
  const [currentImageIndex, setCurrentImageIndex] = useState(currentIndex);

  const handlePrevClick = () => {
    setCurrentImageIndex((prevIndex) =>
      prevIndex === 0 ? images.length - 1 : prevIndex - 1,
    );
  };

  const handleNextClick = () => {
    setCurrentImageIndex((prevIndex) =>
      prevIndex === images.length - 1 ? 0 : prevIndex + 1,
    );
  };

  useEffect(() => {
    onImageSelect(images[currentImageIndex]);
  }, [currentImageIndex, onImageSelect]);

  return (
    <div className="col-span-2 mb-3 flex items-center justify-center">
      <div className="flex items-center justify-center space-x-4">
        <button
          type="button"
          onClick={handlePrevClick}
          aria-label="Previous image"
        >
          <MdChevronLeft className="text-7xl text-medium-green dark:text-dm-light-green" />
        </button>
        <div className="overflow-hidden rounded-full">
          <img
            src={images[currentImageIndex]}
            alt="Avatar"
            className="h-28 rounded-full md:h-32"
            style={{ backgroundColor: bgColor }}
          />
        </div>
        <button type="button" onClick={handleNextClick} aria-label="Next image">
          <MdChevronRight className="text-7xl text-medium-green dark:text-dm-light-green" />
        </button>
      </div>
      <input
        type="color"
        value={bgColor}
        onChange={(e) => setBgColor(e.target.value)}
        aria-label="Background color"
      />
      <input
        type="hidden"
        name="selectedImage"
        value={images[currentImageIndex]}
      />
    </div>
  );
};

export default AvatarSelector;
