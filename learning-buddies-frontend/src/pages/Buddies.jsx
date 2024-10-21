import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { Filters } from "../components/buddies/Filters";
import {
  loadFromLocalStorage,
  saveToLocalStorage,
} from "../utils/storageUtils";
import { BuddyCard } from "../components/buddies/BuddyCard";
import profilesService from "../services/profilesService";
import friendshipService from "../services/friendshipService";
import skillsService from "../services/skillsService";
import toast from "react-hot-toast";
import { MdClose, MdFilterAlt, MdFilterAltOff } from "react-icons/md";

export default function Buddies() {
  const { user, token } = useAuth();
  const [isLoading, setIsLoading] = useState(true);
  const [profiles, setProfiles] = useState([]);
  const [friends, setFriends] = useState();
  const [skills, setSkills] = useState([]);
  const [skills1, setSkills1] = useState(loadFromLocalStorage("skills1") || []);
  const [skills2, setSkills2] = useState(loadFromLocalStorage("skills2") || []);
  const [openFiltersModal, setOpenFiltersModal] = useState(false);

  useEffect(() => {
    saveToLocalStorage("skills1", skills1);
    saveToLocalStorage("skills2", skills2);
  }, [friends, skills1, skills2]);

  const fetchBuddiesAndFriendships = async () => {
    try {
      setIsLoading(true);
      const [profilesData, friendshipsData] = await Promise.all([
        profilesService.getAllProfiles(token, skills1, skills2),
        friendshipService.getFriendships(token),
      ]);
      setFriends(friendshipsData);
      setProfiles(profilesData);
    } catch (error) {
      console.error("Error fetching profiles", error);
      toast.error("No se pudo cargar los perfiles.");
    } finally {
      setIsLoading(false);
    }
  };

  const fetchSkills = async () => {
    try {
      const skillsData = await skillsService.getSkills(token);
      setSkills(skillsData);
    } catch (error) {
      console.error("Error fetching skills", error);
      toast.error("No se pudo cargar las habilidades.");
    }
  };

  useEffect(() => {
    fetchBuddiesAndFriendships();
    fetchSkills();
  }, [skills1, skills2, token]);

  if (isLoading) {
    return <p>Cargando perfiles...</p>;
  }

  const getConnectionStatus = (profileId) => {
    const { friendships, sentRequests, receivedRequests } = friends || {};
    if (friendships?.some((friend) => friend.id === profileId))
      return "Conectado";
    if (sentRequests?.some((request) => request.id === profileId))
      return "Pendiente.";
    if (receivedRequests?.some((request) => request.id === profileId))
      return "Pendiente de aprobación";
    return "Sin conectar";
  };

  const handleSentRequest = async (id, name) => {
    const responseToast = toast.loading("Enviando solicitud...");
    try {
      await friendshipService.sendFriendshipRequest(id, token);
      toast.success(`Solicitud a ${name} enviada!`, { id: responseToast });
      fetchBuddiesAndFriendships();
    } catch (error) {
      console.log(error);
      toast.error(
        `No se pudo enviar solicitud a ${name}. Inténtelo más tarde.`,
        { id: responseToast },
      );
    }
  };

  return (
    <main className="relative my-3 p-4 font-raleway md:p-2 lg:mx-auto lg:max-w-screen-xl">
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
            <button type="button" onClick={() => setOpenFiltersModal(true)}>
              <MdFilterAlt className="mb-1 text-3xl text-dark dark:text-light" />{" "}
            </button>
          </form>
          <div>
            <p className="mt-2 font-medium text-brown dark:text-dm-light-brown">
              CONOCIMIENTOS:
            </p>
            <div className="flex flex-wrap gap-3 py-1">
              {skills1.map((item) => (
                <SkillBadge
                  key={item}
                  skill={item}
                  onRemove={() =>
                    setSkills1(skills1.filter((skill) => skill !== item))
                  }
                />
              ))}
            </div>
            <p className="mt-2 font-medium text-brown dark:text-dm-light-brown">
              APRENDIENDO:
            </p>
            <div className="flex flex-wrap gap-3 py-1">
              {skills2.map((item) => (
                <SkillBadge
                  key={item}
                  skill={item}
                  onRemove={() =>
                    setSkills2(skills2.filter((skill) => skill !== item))
                  }
                />
              ))}
            </div>
          </div>
          <button
            onClick={() => {
              setSkills1([]);
              setSkills2([]);
            }}
            className="mt-2 flex items-center gap-1 font-bold text-brown dark:text-dm-brown"
          >
            Quitar Filtros <MdFilterAltOff />
          </button>
        </div>
        <div className="grid grid-cols-1 justify-center gap-6 p-5 md:grid-cols-2">
          {profiles &&
            profiles.map(
              (item, index) =>
                item.id != user.profileId && (
                  <BuddyCard
                    key={index}
                    profile={item}
                    status={getConnectionStatus(item.id)}
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
          skills={skills}
        />
      )}
    </main>
  );
}

const SkillBadge = ({ skill, onRemove }) => (
  <div className="flex items-center gap-1 rounded-md border-2 border-brown bg-light-brown px-2 font-medium text-dark dark:border-dm-brown dark:bg-light">
    {skill}
    <MdClose className="ml-1 cursor-pointer text-sm" onClick={onRemove} />
  </div>
);
