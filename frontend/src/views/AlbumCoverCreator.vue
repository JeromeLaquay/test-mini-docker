<template>
  
  <!-- Section de création -->
  <section class="py-16 px-4 bg-black/20">
        <div class="max-w-7xl mx-auto">
          <div class="text-center mb-12">
            <h2 class="text-3xl font-bold text-white mb-4">Créez Votre Pochette</h2>
            <p class="text-xl text-gray-300">Commencez en important votre dossier de musique</p>
          </div>
        </div>
      
      <div class="min-h-screen p-4">
    <div class="max-w-[1400px] mx-auto">
      <!-- Panneau principal -->
      <div class="bg-white/10 backdrop-blur-md p-8 rounded-xl shadow-2xl border border-white/20">
        <h3 class="text-2xl font-semibold mb-8 text-white flex items-center">
          <span class="mr-2">🎵</span>
          Création de pochette
        </h3>

        <!-- Layout principal -->
        <div class="flex flex-col lg:flex-row gap-12">
          <!-- Contrôles (gauche) -->
          <div class="lg:w-[500px] space-y-8">
            <!-- Sélection du dossier -->
            <div class="space-y-3">
              <label class="block text-white text-base font-medium">
                <span class="mr-2">📁</span>
                Sélectionnez votre dossier
              </label>
              <div class="relative">
                <input
                  type="file"
                  webkitdirectory
                  directory
                  @change="handleFolderSelect"
                  class="block w-full text-white
                    file:mr-4 file:py-3 file:px-6
                    file:rounded-full file:border-0
                    file:text-sm file:font-semibold
                    file:bg-purple-500 file:text-white
                    hover:file:bg-purple-600
                    file:cursor-pointer
                    cursor-pointer
                    bg-white/5 rounded-lg
                    border border-white/20
                    p-3"
                />
              </div>
            </div>

            <!-- Contrôles de personnalisation -->
            <div class="space-y-6 bg-black/20 p-6 rounded-lg">
              <div class="flex flex-col md:flex-row md:items-center justify-between mb-6 gap-4">
                <h4 class="text-white text-base font-medium flex items-center">
                  <span class="mr-2">⚙️</span>
                  Personnalisation
                </h4>
                <div class="flex flex-wrap gap-2">
                  <button
                    @click="applyPreset('default')"
                    class="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-all duration-200 transform hover:scale-105 shadow-lg hover:shadow-purple-500/50 font-medium min-w-[120px]"
                  >
                    Preset par défaut
                  </button>
                  <button
                    @click="applyPreset('light')"
                    class="px-4 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition-all duration-200 transform hover:scale-105 shadow-lg hover:shadow-gray-400/50 font-medium min-w-[120px]"
                  >
                    Preset clair
                  </button>
                  <button
                    @click="applyPreset('dark')"
                    class="px-4 py-2 bg-gray-800 text-white rounded-lg hover:bg-gray-900 transition-all duration-200 transform hover:scale-105 shadow-lg hover:shadow-gray-800/50 font-medium min-w-[120px]"
                  >
                    Preset sombre
                  </button>
                </div>
              </div>

              <!-- Couleur et alignement du titre -->
              <div class="space-y-3 bg-white/5 p-4 rounded-lg">
                <div class="flex items-center gap-2">
                  <input type="color" v-model="titleColor" class="w-8 h-8 rounded cursor-pointer">
                  <input type="text" v-model="titleColor" class="flex-1 bg-white/10 text-white px-3 py-2 rounded-lg text-sm border border-white/10 focus:border-purple-500 focus:ring-1 focus:ring-purple-500 outline-none" placeholder="#FFFFFF">
                </div>
                <div class="flex bg-white/10 rounded-lg p-1">
                  <button 
                    v-for="align in ['left', 'center', 'right']"
                    :key="align"
                    @click="titleAlignment = align"
                    :class="[
                      'flex-1 px-3 py-2 rounded-lg transition-all duration-200 text-sm font-medium',
                      titleAlignment === align ? 'bg-purple-600 text-white shadow-lg' : 'text-white/70 hover:text-white hover:bg-white/5'
                    ]"
                  >
                    {{ align === 'left' ? '⬅️' : align === 'center' ? '↔️' : '➡️' }}
                  </button>
                </div>
              </div>

              <!-- Couleur et alignement des musiques -->
              <div class="space-y-3 bg-white/5 p-4 rounded-lg">
                <div class="flex items-center gap-2">
                  <input type="color" v-model="songColor" class="w-8 h-8 rounded cursor-pointer">
                  <input type="text" v-model="songColor" class="flex-1 bg-white/10 text-white px-3 py-2 rounded-lg text-sm border border-white/10 focus:border-purple-500 focus:ring-1 focus:ring-purple-500 outline-none" placeholder="#FFFFFF">
                </div>
                <div class="flex bg-white/10 rounded-lg p-1">
                  <button 
                    v-for="align in ['left', 'center', 'right']"
                    :key="align"
                    @click="songsAlignment = align"
                    :class="[
                      'flex-1 px-3 py-2 rounded-lg transition-all duration-200 text-sm font-medium',
                      songsAlignment === align ? 'bg-purple-600 text-white shadow-lg' : 'text-white/70 hover:text-white hover:bg-white/5'
                    ]"
                  >
                    {{ align === 'left' ? '⬅️' : align === 'center' ? '↔️' : '➡️' }}
                  </button>
                </div>
              </div>

              <!-- Opacités -->
              <div class="space-y-4 bg-white/5 p-4 rounded-lg">
                <div class="space-y-2">
                  <div class="flex justify-between text-white/70 text-sm font-medium">
                    <span>Fond du titre</span>
                    <span>{{ titleBgOpacity }}%</span>
                  </div>
                  <input 
                    type="range" 
                    v-model="titleBgOpacity" 
                    min="0" 
                    max="100" 
                    class="w-full accent-purple-600"
                  >
                </div>

                <div class="space-y-2">
                  <div class="flex justify-between text-white/70 text-sm font-medium">
                    <span>Fond des musiques</span>
                    <span>{{ songsBgOpacity }}%</span>
                  </div>
                  <input 
                    type="range" 
                    v-model="songsBgOpacity" 
                    min="0" 
                    max="100" 
                    class="w-full accent-purple-600"
                  >
                </div>

                <div class="space-y-2">
                  <div class="flex justify-between text-white/70 text-sm font-medium">
                    <span>Image de fond</span>
                    <div class="flex items-center gap-3">
                      <button
                        @click="toggleBackgroundImage"
                        :class="[
                          'flex items-center gap-2 px-3 py-1 rounded-full text-xs font-medium transition-all duration-200',
                          showBackgroundImage ? 'bg-purple-600 text-white' : 'bg-white/10 text-white/70 hover:bg-white/20'
                        ]"
                      >
                        <span>{{ showBackgroundImage ? '👁️' : '👁️‍🗨️' }}</span>
                        {{ showBackgroundImage ? 'Visible' : 'Masquée' }}
                      </button>
                      <span>{{ backgroundImageOpacity }}%</span>
                    </div>
                  </div>
                  <input 
                    type="range" 
                    v-model="backgroundImageOpacity" 
                    min="0" 
                    max="100" 
                    class="w-full accent-purple-600"
                    :disabled="!showBackgroundImage"
                  >
                </div>
              </div>

              <!-- Superposition -->
              <div class="space-y-3">
                <div class="flex items-center gap-2">
                  <input type="color" v-model="overlayColor" class="w-8 h-8 rounded cursor-pointer">
                  <input type="text" v-model="overlayColor" class="flex-1 bg-white/10 text-white px-2 py-1 rounded text-sm" placeholder="#000000">
                </div>
                <div class="space-y-2">
                  <div class="flex justify-between text-white/70 text-sm">
                    <span>Opacité</span>
                    <span>{{ overlayOpacity }}%</span>
                  </div>
                  <input 
                    type="range" 
                    v-model="overlayOpacity" 
                    min="0" 
                    max="100" 
                    class="w-full accent-purple-600"
                  >
                </div>
              </div>
            </div>
          </div>

          <!-- Prévisualisation (droite) -->
          <div class="flex-1 flex flex-col items-center justify-center">
            <div v-if="selectedFolder" 
                 class="w-[600px] h-[600px] bg-black/40 border-2 border-white/30 rounded-lg shadow-2xl overflow-hidden p-6 relative backdrop-blur-sm preview-container"
            >
              <div v-if="backgroundImage" 
                   class="absolute inset-0 bg-cover bg-center"
                   :style="{ 
                     backgroundImage: showBackgroundImage ? `url(${backgroundImage})` : 'none',
                     opacity: backgroundImageOpacity/100
                   }">
              </div>
              <div class="absolute inset-0"
                   :style="{ 
                     backgroundColor: overlayColor,
                     opacity: overlayOpacity/100
                   }">
              </div>
              <div class="w-full h-full flex flex-col relative z-10" :style="{ gap: spacing }">
                <div class="rounded-lg mb-4" :style="{ backgroundColor: `rgba(0, 0, 0, ${titleBgOpacity/100})` }">
                  <h5 class="font-bold p-4" :style="{ 
                    color: titleColor,
                    textAlign: titleAlignment,
                    fontSize: titleFontSize
                  }">
                    {{ selectedFolder.name }}
                  </h5>
                </div>
                <div class="flex-1 overflow-y-auto w-full rounded-lg p-4 flex items-center justify-center" 
                     :style="{ 
                       backgroundColor: `rgba(0, 0, 0, ${songsBgOpacity/100})`,
                       alignSelf: songsPosition === 'bottom' ? 'flex-end' : 'auto'
                     }">
                  <div :class="{'w-1/2': audioFiles.length <= 10, 'w-full': audioFiles.length > 10}">
                    <div :class="{'block': audioFiles.length <= 10, 'grid grid-cols-2 gap-8': audioFiles.length > 10}">
                      <div class="flex flex-col">
                        <ul class="space-y-3">
                          <li v-for="file in firstColumn" :key="file" 
                              class="border-b border-white/20 pb-2 text-center"
                              :style="{ 
                                color: songColor,
                                fontSize: songFontSize
                              }">
                            {{ getFileNameWithoutExtension(file) }}
                          </li>
                        </ul>
                      </div>
                      <div v-if="audioFiles.length > 10" class="flex flex-col">
                        <ul class="space-y-3">
                          <li v-for="file in secondColumn" :key="file" 
                              class="border-b border-white/20 pb-2 text-center"
                              :style="{ 
                                color: songColor,
                                fontSize: songFontSize
                              }">
                            {{ getFileNameWithoutExtension(file) }}
                          </li>
                        </ul>
                      </div>
                    </div>
                  </div>
                  <p v-if="audioFiles.length > 20" class="text-amber-400 text-sm mt-4 text-center absolute bottom-2 left-0 right-0">
                    Seules les 20 premières musiques seront affichées
                  </p>
                </div>
              </div>
            </div>
            <p class="mt-3 text-sm text-white/70">Format professionnel : 12x12 cm (600x600 px)</p>

            <div class="flex gap-4 mt-6">
              <div class="relative inline-block">
                <button
                  @click="exportAsPDF"
                  class="bg-purple-600 hover:bg-purple-700 text-white font-bold py-3 px-8 rounded-full transition-colors duration-200 flex items-center gap-2 text-lg"
                  :disabled="isLoading || !selectedFolder"
                >
                  <span>📥</span>
                  Exporter
                </button>
              </div>

              <button
                @click="saveAlbum"
                class="bg-purple-600 hover:bg-purple-700 text-white font-bold py-3 px-8 rounded-full transition-colors duration-200 flex items-center gap-2 text-lg"
                :disabled="isLoading || !selectedFolder"
              >
                <span>💾</span>
                Sauvegarder
              </button>
            </div>

            <div v-if="error" class="mt-4 p-4 bg-red-500/20 text-red-200 rounded-lg border border-red-500/30">
              {{ error }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- Section Albums Sauvegardés -->
<section v-if="userAlbums.length > 0" class="py-16 px-4 bg-black/20">
        <div class="max-w-7xl mx-auto">
          <h2 class="text-3xl font-bold text-white text-center mb-12">Vos Albums</h2>
          
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <div v-for="album in userAlbums" 
                 :key="album.id" 
                 class="bg-white/10 backdrop-blur-sm p-6 rounded-xl border border-white/20">
              <div class="aspect-square mb-4 relative overflow-hidden rounded-lg">
                <img 
                  :src="album.backgroundImage" 
                  alt="Pochette d'album"
                  class="w-full h-full object-cover"
                />
                <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
                <h3 class="absolute bottom-4 left-4 right-4 text-white font-bold text-lg">
                  {{ album.name }}
                </h3>
              </div>
              
              <div class="flex justify-between items-center">
                <button
                  @click="$router.push(`/album/${album.id}`)"
                  class="text-blue-400 hover:text-blue-300 transition-colors"
                >
                  Modifier
                </button>
                <button
                  @click="deleteAlbum(album.id)"
                  class="text-red-400 hover:text-red-300 transition-colors"
                >
                  Supprimer
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
</template>

<script>
import { ref, computed, onBeforeUnmount, onMounted } from 'vue';
import { useToast } from 'vue-toastification';
import { useStore } from 'vuex';
import html2canvas from 'html2canvas';
import { jsPDF } from 'jspdf';
import astroDjImage from '../assets/astro-dj.png';

export default {
  name: 'AlbumCoverCreator',
  setup() {
    const toast = useToast();
    const store = useStore();
    const selectedFolder = ref(null);
    const isLoading = ref(false);
    const error = ref(null);
    const backgroundImage = ref(null);
    const audioFiles = ref([]);
    const userAlbums = ref([]);
    
    // Propriétés de personnalisation
    const titleColor = ref('#FFFFFF');
    const songColor = ref('#FFFFFF');
    const titleBgOpacity = ref(60);
    const songsBgOpacity = ref(60);
    const backgroundImageOpacity = ref(30);
    const overlayColor = ref('#000000');
    const overlayOpacity = ref(30);
    const titleAlignment = ref('center');
    const songsAlignment = ref('left');
    const showExportMenu = ref(false);
    const showBackgroundImage = ref(true);
    const previousBackgroundImageOpacity = ref(null);
    
    // Ajout des nouvelles variables
    const titleFontSize = ref('1.5rem');
    const songFontSize = ref('0.8rem');
    const titlePosition = ref('center');
    const songsPosition = ref('bottom');
    const spacing = ref('1.5rem');

    const singleColumn = computed(() => {
      if (!audioFiles.value || audioFiles.value.length === 0) {
        return [];
      }
      if (audioFiles.value.length < 10) {
        return audioFiles.value.slice(0, 20);
      }
      return firstColumn.value;
    });

    const firstColumn = computed(() => {
      if (!audioFiles.value || audioFiles.value.length === 0) {
        return [];
      }
      if (audioFiles.value.length <= 10) {
        return audioFiles.value.slice(0, Math.min(audioFiles.value.length, 20));
      }
      return audioFiles.value.slice(0, 10);
    });

    const secondColumn = computed(() => {
      if (!audioFiles.value || audioFiles.value.length === 0) {
        return [];
      }
      if (audioFiles.value.length <= 10) {
        return [];
      }
      return audioFiles.value.slice(10, 20);
    });

    const handleFolderSelect = async (event) => {
      const files = Array.from(event.target.files || []);
      if (files.length > 0) {
        try {
          const folderPath = files[0].webkitRelativePath.split('/')[0];
          
          // Filtrer les fichiers audio
          const filteredFiles = files
            .filter(file => {
              const extension = file.name.toLowerCase().split('.').pop();
              return ['mp3', 'wav', 'ogg', 'm4a', 'flac', 'txt'].includes(extension);
            })
            .map(file => file.name);

          audioFiles.value = filteredFiles;

          // Chercher une image de couverture
          const imageFile = files.find(file => {
            const extension = file.name.toLowerCase().split('.').pop();
            return ['png', 'jpg', 'jpeg', 'webp'].includes(extension);
          });

          if (imageFile) {
            if (backgroundImage.value && backgroundImage.value.startsWith('blob:')) {
              URL.revokeObjectURL(backgroundImage.value);
            }
            backgroundImage.value = URL.createObjectURL(imageFile);
          } else {
            backgroundImage.value = null;
          }

          selectedFolder.value = {
            name: folderPath,
            files: filteredFiles
          };
          error.value = null;
          toast.success('Dossier chargé avec succès !');
        } catch (err) {
          console.error('Erreur lors du chargement du dossier:', err);
          toast.error('Erreur lors du chargement du dossier');
          error.value = 'Erreur lors du chargement du dossier';
        }
      }
    };

    const getFileNameWithoutExtension = (filename) => {
      return filename.replace(/\.[^/.]+$/, "");
    };

    const saveAlbum = async () => {
      if (!selectedFolder.value) {
        toast.error('Veuillez d\'abord sélectionner un dossier');
        return;
      }

      try {
        isLoading.value = true;
        error.value = null;

        const albumRequest = {
          name: selectedFolder.value.name,
          titleColor: titleColor.value,
          songColor: songColor.value,
          titleAlignment: titleAlignment.value,
          songsAlignment: songsAlignment.value,
          titleBackgroundOpacity: titleBgOpacity.value,
          songsBackgroundOpacity: songsBgOpacity.value,
          backgroundImageOpacity: backgroundImageOpacity.value,
          overlayColor: overlayColor.value,
          overlayOpacity: overlayOpacity.value,
          songs: audioFiles.value,
          backgroundImage: backgroundImage.value
        };

        await store.dispatch('album/saveAlbumToStore', albumRequest);
        toast.success('Album sauvegardé avec succès !');
        
      } catch (err) {
        console.error('Erreur lors de la sauvegarde:', err);
        error.value = err.response?.data?.message || 'Erreur lors de la sauvegarde de la pochette';
        toast.error(error.value);
      } finally {
        isLoading.value = false;
      }
    };

    const loadSavedAlbum = async (albumId) => {
      try {
        const albumData = await store.dispatch('album/getAlbum', albumId);
        
        titleColor.value = albumData.titleColor;
        songColor.value = albumData.songColor;
        titleAlignment.value = albumData.titleAlignment;
        songsAlignment.value = albumData.songsAlignment;
        titleBgOpacity.value = albumData.titleBackgroundOpacity;
        songsBgOpacity.value = albumData.songsBackgroundOpacity;
        backgroundImageOpacity.value = albumData.backgroundImageOpacity;
        overlayColor.value = albumData.overlayColor;
        overlayOpacity.value = albumData.overlayOpacity;
        backgroundImage.value = albumData.backgroundImage;
        audioFiles.value = albumData.songs;
        selectedFolder.value = {
          name: albumData.name,
          files: albumData.songs
        };
        toast.success('Album chargé avec succès !');
      } catch (err) {
        console.error('Erreur lors du chargement de l\'album:', err);
        toast.error('Erreur lors du chargement de l\'album');
      }
    };

    const exportAsPDF = async () => {
      if (!selectedFolder.value) {
        toast.error('Veuillez d\'abord sélectionner un dossier');
        return;
      }

      try {
        isLoading.value = true;
        error.value = null;

        const canvas = await capturePreview();
        const imgData = canvas.toDataURL('image/png', 1.0);

        // Créer le PDF au format 12x12 cm
        const pdf = new jsPDF({
          orientation: 'portrait',
          unit: 'cm',
          format: [12, 12]  // Format carré 12x12 cm
        });

        // Ajouter l'image au PDF
        pdf.addImage(imgData, 'PNG', 0, 0, 12, 12);

        // Sauvegarder le PDF
        pdf.save(`${selectedFolder.value.name}_pochette.pdf`);
        toast.success('PDF exporté avec succès !');
      } catch (err) {
        console.error('Erreur lors de l\'export PDF:', err);
        error.value = `Erreur lors de l'export PDF: ${err.message}`;
        toast.error(error.value);
      } finally {
        isLoading.value = false;
      }
    };

    const capturePreview = async () => {
      const previewElement = document.querySelector('.preview-container');
      if (!previewElement) {
        throw new Error("Élément de prévisualisation non trouvé");
      }

      return await html2canvas(previewElement, {
        scale: 4,
        useCORS: true,
        allowTaint: true,
        backgroundColor: '#111827',
        logging: false,
        width: 600,
        height: 600
      });
    };

    const applyPreset = (type) => {
      switch (type) {
        case 'light':
          titleColor.value = '#FFFFFF';
          songColor.value = '#FFFFFF';
          overlayColor.value = '#000000';
          titleBgOpacity.value = 100;
          songsBgOpacity.value = 100;
          overlayOpacity.value = 100;
          backgroundImageOpacity.value = showBackgroundImage.value ? 100 : 0;
          showBackgroundImage.value = true;
          break;
        case 'dark':
          titleColor.value = '#000000';
          songColor.value = '#000000';
          overlayColor.value = '#FFFFFF';
          titleBgOpacity.value = 0;
          songsBgOpacity.value = 0;
          overlayOpacity.value = 100;
          backgroundImageOpacity.value = 100;
          showBackgroundImage.value = false;
          break;
        default:
          titleColor.value = '#FFFFFF';
          songColor.value = '#E5E5E5';
          overlayColor.value = '#1A1A1A';
          titleBgOpacity.value = 80;
          songsBgOpacity.value = 60;
          overlayOpacity.value = 50;
          backgroundImageOpacity.value = 90;
          showBackgroundImage.value = true;
      }

      titleFontSize.value = '1.5rem';
      songFontSize.value = '0.8rem';
      titlePosition.value = 'center';
      songsPosition.value = 'bottom';
      spacing.value = '1.5rem';
    };

    const toggleBackgroundImage = () => {
      showBackgroundImage.value = !showBackgroundImage.value;
      if (!showBackgroundImage.value) {
        previousBackgroundImageOpacity.value = backgroundImageOpacity.value;
        backgroundImageOpacity.value = 0;
      } else {
        backgroundImageOpacity.value = previousBackgroundImageOpacity.value || 100;
      }
    };

    const initDemoFolder = () => {
      selectedFolder.value = {
        name: "Astro DJ Mix",
        files: [
          "01. Space Intro.mp3",
          "02. Cosmic Beats.mp3",
          "03. Stellar Groove.mp3",
          "04. Moonlight Mix.mp3",
          "05. Galaxy Sound.mp3",
          "06. Orbital Rhythm.mp3",
          "07. Deep Space Bass.mp3",
          "08. Astronaut's Dream.mp3",
          "09. Starlight Serenade.mp3",
          "10. Galactic Groove.mp3",
          "11. Cosmic Journey.mp3",
          "12. Stellar Symphony.mp3"
        ]
      };
      audioFiles.value = selectedFolder.value.files;
      
      // Appliquer des styles adaptés à l'image spatiale
      titleColor.value = '#FFFFFF';
      songColor.value = '#FFFFFF';
      overlayColor.value = '#000033';
      titleBgOpacity.value = 70;
      songsBgOpacity.value = 50;
      overlayOpacity.value = 30;
      backgroundImageOpacity.value = 80;
      showBackgroundImage.value = true;

      // Utiliser l'image de l'astronaute DJ
      backgroundImage.value = astroDjImage;
    };

    const closeExportMenu = () => {
      showExportMenu.value = false;
    };

    const toggleExportMenu = (event) => {
      event.stopPropagation();
      showExportMenu.value = !showExportMenu.value;
    };

    const loadUserAlbums = async () => {
      try {
        console.log("Chargement des albums de l'utilisateur...");
        const albums = await store.dispatch('album/fetchUserAlbums');
        userAlbums.value = albums;
      } catch (err) {
        console.error('Erreur lors du chargement des albums:', err);
        toast.error('Impossible de charger vos albums');
      }
    };

    const deleteAlbum = async (albumId) => {
      try {
        await store.dispatch('album/deleteAlbum', albumId);
        toast.success('Album supprimé avec succès');
        await loadUserAlbums(); // Recharger la liste après suppression
      } catch (err) {
        console.error('Erreur lors de la suppression:', err);
        toast.error('Impossible de supprimer l\'album');
      }
    };

    onMounted(() => {
      document.addEventListener('click', closeExportMenu);
      loadUserAlbums();
      // Initialiser la démo si aucun dossier n'est sélectionné
      if (!selectedFolder.value) {
        initDemoFolder();
      }
    });

    onBeforeUnmount(() => {
      document.removeEventListener('click', closeExportMenu);
      if (backgroundImage.value && backgroundImage.value.startsWith('blob:')) {
        URL.revokeObjectURL(backgroundImage.value);
      }
    });

    return {
      selectedFolder,
      isLoading,
      error,
      backgroundImage,
      audioFiles,
      titleColor,
      songColor,
      titleBgOpacity,
      songsBgOpacity,
      backgroundImageOpacity,
      overlayColor,
      overlayOpacity,
      titleAlignment,
      songsAlignment,
      titleFontSize,
      songFontSize,
      titlePosition,
      songsPosition,
      spacing,
      singleColumn,
      firstColumn,
      secondColumn,
      userAlbums,
      handleFolderSelect,
      getFileNameWithoutExtension,
      saveAlbum,
      loadSavedAlbum,
      showExportMenu,
      toggleExportMenu,
      exportAsPDF,
      applyPreset,
      showBackgroundImage,
      toggleBackgroundImage,
      initDemoFolder,
      deleteAlbum
    };
  }
};
</script>

<style scoped>
.overflow-y-auto::-webkit-scrollbar {
  width: 8px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 4px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.4);
}

.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.z-50 {
  z-index: 50;
}

.inline-block {
  display: inline-block;
}

.accent-purple-600::-webkit-slider-thumb {
  background: #9333ea;
}
.accent-purple-600::-moz-range-thumb {
  background: #9333ea;
}
.accent-purple-600::-ms-thumb {
  background: #9333ea;
}

input[type="range"] {
  height: 4px;
  border-radius: 2px;
  background: rgba(255, 255, 255, 0.1);
}

input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 16px;
  height: 16px;
  border-radius: 50%;
  cursor: pointer;
}

input[type="range"]::-moz-range-thumb {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  cursor: pointer;
  border: none;
}

input[type="range"]::-ms-thumb {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  cursor: pointer;
}
</style> 