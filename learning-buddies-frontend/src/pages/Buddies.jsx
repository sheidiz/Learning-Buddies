import { useEffect, useState } from "react";
import { MdClose, MdFilterAlt, MdFilterAltOff } from "react-icons/md";
import { Filters } from "../components/buddies/Filters";
import {
  loadFromLocalStorage,
  saveToLocalStorage,
} from "../utils/storageUtils";
import profilesService from "../services/profilesService";
import { BuddyCard } from "../components/buddies/BuddyCard";
import { useAuth } from "../contexts/AuthContext";
import friendshipService from "../services/friendshipService";
import { useNavigate } from "react-router-dom";

export default function Buddies() {
  const navigate = useNavigate();
  const { user, token } = useAuth();
  const [isLoading, setIsLoading] = useState(true);
  const [profiles, setProfiles] = useState([]);
  const [skills1, setSkills1] = useState(
    () => loadFromLocalStorage("skills1") || [],
  );
  const [skills2, setSkills2] = useState(
    () => loadFromLocalStorage("skills2") || [],
  );
  const [openFiltersModal, setOpenFiltersModal] = useState(false);

  useEffect(() => {
    saveToLocalStorage("skills1", skills1);
    saveToLocalStorage("skills2", skills2);
  }, [skills1, skills2]);

  const handleRemoveSkill1 = (skill) => {
    setSkills1((prev) => prev.filter((item) => item !== skill));
  };

  const handleRemoveSkill2 = (skill) => {
    setSkills2((prev) => prev.filter((item) => item !== skill));
  };

  const handleRemoveFilters = () => {
    setSkills1([]);
    setSkills2([]);
  };

  const openModal = () => {
    setOpenFiltersModal(true);
  };

  useEffect(() => {
    const fetchBuddies = async () => {
      try {
        const profilesData = await profilesService.getAllProfiles(token);
        setProfiles(profilesData);
      } catch (error) {
        console.error("Error fetching profiles", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBuddies();
  }, [isLoading]);

  if (isLoading) {
    return <p>Cargando perfiles...</p>;
  }

  const handleSentRequest = async (id, name) => {
    try {
      await friendshipService.sendFriendshipRequest(id, token);
      alert("se envio la solicitud a" + name);
      navigate("/buddies");
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <main className="my-3 p-4 font-raleway md:p-2 lg:mx-auto lg:max-w-screen-xl">
      <h2
        className={`text-center text-2xl font-semibold text-medium-green md:mb-1 dark:text-dm-light-green ${openFiltersModal && "blur-sm"}`}
      >
        Encontrá a tu próximo compañero de estudios
      </h2>
      <div
        className={`flex flex-col gap-2 md:flex-row ${openFiltersModal && "blur-sm"}`}
      >
        <div className="md:w-1/2 lg:w-1/3">
          <h3 className="hidden text-xl font-semibold text-dark md:block dark:text-light">
            Filtros
          </h3>
          <form className="flex items-center gap-2 py-3 md:py-2">
            <input
              type="text"
              name="inputSearch"
              className="w-full rounded-3xl px-5 py-1 text-center"
              placeholder="Buscar por rol"
            />
            <button type="button" onClick={openModal}>
              <MdFilterAlt className="mb-1 text-3xl text-dark dark:text-light" />{" "}
            </button>
          </form>
          <p className="mt-2 font-medium text-brown dark:text-dm-light-brown">
            CONOCIMIENTOS:
          </p>
          <div className="flex flex-wrap gap-3 py-1">
            {skills1.map((item, index) => (
              <div
                key={index}
                className="flex items-center gap-1 rounded-md border-2 border-brown bg-light-brown px-2 font-medium text-dark dark:border-dm-brown dark:bg-light"
              >
                {item}
                <MdClose
                  className="ml-1 cursor-pointer text-sm"
                  onClick={() => handleRemoveSkill1(item)}
                />
              </div>
            ))}
          </div>
          <p className="mt-2 font-medium text-brown dark:text-dm-light-brown">
            APRENDIENDO:
          </p>
          <div className="flex flex-wrap gap-3 py-1">
            {skills2.map((item, index) => (
              <div
                key={index}
                className="flex items-center gap-1 rounded-md border-2 border-brown bg-light-brown px-2 font-medium text-dark dark:border-dm-brown dark:bg-light"
              >
                {item}
                <MdClose
                  className="ml-1 cursor-pointer text-sm"
                  onClick={() => handleRemoveSkill2(item)}
                />
              </div>
            ))}
          </div>
          <button
            onClick={handleRemoveFilters}
            className="mt-2 flex items-center gap-1 font-bold text-brown dark:text-dm-brown"
          >
            Quitar Filtros <MdFilterAltOff />{" "}
          </button>
        </div>
        <div className="grid grid-cols-1 justify-center gap-6 p-5 md:grid-cols-2">
          {profiles &&
            profiles.map(
              (item, index) =>
                item.id != user.profile.id && (
                  <BuddyCard
                    key={index}
                    profile={item}
                    contactable={true}
                    onClick={handleSentRequest}
                  />
                ),
            )}
        </div>
      </div>
      {openFiltersModal && (
        <Filters
          setOpenModal={setOpenFiltersModal}
          skills1={skills1}
          skills2={skills2}
          setSkills1={setSkills1}
          setSkills2={setSkills2}
        />
      )}
    </main>
  );
}
