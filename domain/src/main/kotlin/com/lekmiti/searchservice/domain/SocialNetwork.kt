package com.lekmiti.searchservice.domain


data class SocialNetwork(val type: String, val link: String) {
    companion object {
        internal fun mergeSocialNetworks(oldSocialNetworks: SocialNetworks, newSocialNetworks: SocialNetworks) =
            newSocialNetworks
                .filter { oldSocialNetworks.none { old -> old.type == it.type } }
                .plus(oldSocialNetworks)
    }
}